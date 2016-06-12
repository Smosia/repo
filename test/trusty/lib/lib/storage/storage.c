/*
 * Copyright (C) 2015 The Android Open Source Project
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

#include <err.h>
#include <stdint.h>

#include <lib/storage/storage.h>

int storage_open_session(storage_session_t *session_p)
{
	return ERR_NOT_IMPLEMENTED;
}

void storage_close_session(storage_session_t session)
{

}

int storage_open_file(storage_session_t session, file_handle_t *handle_p, const char *name,
                      uint32_t flags)
{
	return ERR_NOT_IMPLEMENTED;
}


void storage_close_file(storage_session_t session, file_handle_t handle)
{
}

int storage_delete_file(storage_session_t session, const char *name)
{
	return ERR_NOT_IMPLEMENTED;
}

int storage_read(storage_session_t session, file_handle_t handle,
                  storage_off_t off, void *buf, size_t size)
{
	return ERR_NOT_IMPLEMENTED;
}

int storage_write(storage_session_t session, file_handle_t handle,
                  storage_off_t off, void *buf, size_t size)
{
	return ERR_NOT_IMPLEMENTED;
}

int storage_set_file_size(storage_session_t session, file_handle_t handle,
                          storage_off_t file_size)
{
	return ERR_NOT_IMPLEMENTED;
}

int storage_get_file_size(storage_session_t session, file_handle_t handle,
                           storage_off_t *size)
{
	return ERR_NOT_IMPLEMENTED;
}

