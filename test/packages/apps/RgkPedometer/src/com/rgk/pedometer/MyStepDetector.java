package com.rgk.pedometer;

import android.hardware.SensorEvent;

public class MyStepDetector extends StepDetector {

	float[] oriValues = new float[3];
	final int valueNum = 4;
	// ���ڴ�ż�����ֵ�Ĳ��岨�Ȳ�ֵ
	float[] tempValue = new float[valueNum];
	int tempCount = 0;
	// �Ƿ������ı�־λ
	boolean isDirectionUp = false;
	// ������������
	int continueUpCount = 0;
	// ��һ��ĳ��������Ĵ�����Ϊ�˼�¼�������������
	int continueUpFormerCount = 0;
	// ��һ���״̬�����������½�
	boolean lastStatus = false;
	// ����ֵ
	float peakOfWave = 0;
	// ����ֵ
	float valleyOfWave = 0;
	// �˴β����ʱ��
	long timeOfThisPeak = 0;
	// �ϴβ����ʱ��
	long timeOfLastPeak = 0;
	// ��ǰ��ʱ��
	long timeOfNow = 0;
	// ��ǰ��������ֵ
	float gravityNew = 0;
	// �ϴδ�������ֵ
	float gravityOld = 0;
	// ��̬��ֵ��Ҫ��̬�����ݣ����ֵ������Щ��̬���ݵ���ֵ
	final float initialValue = (float) 1.3;
	// ��ʼ��ֵ
	float ThreadValue = (float) 2.0;

	// private StepListener mStepListeners;

	public MyStepDetector() {
		super();
	}

	/*
	 * ע����G-Sensor��һֻ������������ ���������ݽ���ƽ���Ϳ����ŵĴ��� ����DetectorNewStep��ⲽ��
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		for (int i = 0; i < 3; i++) {
			oriValues[i] = event.values[i];
		}
		gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
				+ oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
		detectorNewStep(gravityNew);
	}

	/*
	 * ��ⲽ�ӣ�����ʼ�Ʋ� 1.����sersor�е����� 2.�����⵽�˲��壬���ҷ���ʱ����Լ���ֵ�����������ж�Ϊ1��
	 * 3.����ʱ������������岨�Ȳ�ֵ����initialValue���򽫸ò�ֵ������ֵ�ļ�����
	 */
	public void detectorNewStep(float values) {
		if (gravityOld == 0) {
			gravityOld = values;
		} else {
			if (detectorPeak(values, gravityOld)) {
				timeOfLastPeak = timeOfThisPeak;
				timeOfNow = System.currentTimeMillis();
				if (timeOfNow - timeOfLastPeak >= 250
						&& (peakOfWave - valleyOfWave >= ThreadValue)) {
					timeOfThisPeak = timeOfNow;
					/*
					 * ���½���Ĵ������漰���㷨 һ����֪ͨ���½���֮ǰ���������洦��Ϊ�˴�����Ч�˶��� 1.������¼10�ſ�ʼ�Ʋ�
					 * 2.�����¼��9���û�ͣס����3�룬��ǰ��ļ�¼ʧЧ���´δ�ͷ��ʼ
					 * 3.������¼��9���û������˶���֮ǰ�����ݲ���Ч
					 */
					// mStepListeners.onStep();
					for (StepListener stepListener : mStepListeners) {
						stepListener.onStep();
					}
				}
				if (timeOfNow - timeOfLastPeak >= 250
						&& (peakOfWave - valleyOfWave >= initialValue)) {
					timeOfThisPeak = timeOfNow;
					ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
				}
			}
		}
		gravityOld = values;
	}

	/*
	 * ��Ⲩ�� �����ĸ������ж�Ϊ���壺 1.Ŀǰ��Ϊ�½������ƣ�isDirectionUpΪfalse
	 * 2.֮ǰ�ĵ�Ϊ���������ƣ�lastStatusΪtrue 3.������Ϊֹ�������������ڵ���2�� 4.����ֵ����20 ��¼����ֵ
	 * 1.�۲첨��ͼ�����Է����ڳ��ֲ��ӵĵط������ȵ���һ�����ǲ��壬�бȽ����Ե������Լ���ֵ 2.����Ҫ��¼ÿ�εĲ���ֵ��Ϊ�˺��´εĲ������Ա�
	 */
	public boolean detectorPeak(float newValue, float oldValue) {
		lastStatus = isDirectionUp;
		if (newValue >= oldValue) {
			isDirectionUp = true;
			continueUpCount++;
		} else {
			continueUpFormerCount = continueUpCount;
			continueUpCount = 0;
			isDirectionUp = false;
		}

		if (!isDirectionUp && lastStatus
				&& (continueUpFormerCount >= 2 || oldValue >= 20)) {
			peakOfWave = oldValue;
			return true;
		} else if (!lastStatus && isDirectionUp) {
			valleyOfWave = oldValue;
			return false;
		} else {
			return false;
		}
	}

	/*
	 * ��ֵ�ļ��� 1.ͨ�����岨�ȵĲ�ֵ������ֵ 2.��¼4��ֵ������tempValue[]������
	 * 3.�ڽ����鴫�뺯��averageValue�м�����ֵ
	 */
	public float peakValleyThread(float value) {
		float tempThread = ThreadValue;
		if (tempCount < valueNum) {
			tempValue[tempCount] = value;
			tempCount++;
		} else {
			tempThread = averageValue(tempValue, valueNum);
			for (int i = 1; i < valueNum; i++) {
				tempValue[i - 1] = tempValue[i];
			}
			tempValue[valueNum - 1] = value;
		}
		return tempThread;

	}

	/*
	 * �ݶȻ���ֵ 1.��������ľ�ֵ 2.ͨ����ֵ����ֵ�ݶȻ���һ����Χ��
	 */
	public float averageValue(float value[], int n) {
		float ave = 0;
		for (int i = 0; i < n; i++) {
			ave += value[i];
		}
		ave = ave / valueNum;
		if (ave >= 8)
			ave = (float) 4.3;
		else if (ave >= 7 && ave < 8)
			ave = (float) 3.3;
		else if (ave >= 4 && ave < 7)
			ave = (float) 2.3;
		else if (ave >= 3 && ave < 4)
			ave = (float) 2.0;
		else {
			ave = (float) 1.3;
		}
		return ave;
	}

}
