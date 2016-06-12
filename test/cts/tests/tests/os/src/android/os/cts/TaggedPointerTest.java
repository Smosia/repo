/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.os.cts;


import android.test.AndroidTestCase;

import android.os.cts.CpuFeatures;
import android.os.cts.TaggedPointer;

public class TaggedPointerTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testHasTaggedPointer() {
        if (!CpuFeatures.isArm64Cpu()) {
            return;
        }
        assertTrue("Machine does not support tagged pointers", TaggedPointer.hasTaggedPointer());
    }
}
