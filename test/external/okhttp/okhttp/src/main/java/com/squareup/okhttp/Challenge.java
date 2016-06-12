/*
 * Copyright (C) 2014 Square, Inc.
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
package com.squareup.okhttp;

import static com.squareup.okhttp.internal.Util.equal;

/** An RFC 2617 challenge. */
public final class Challenge {
  private final String scheme;
  private final String realm;
  private final String nonce;
  private final String stale;
  private final String qop;
  private final String opaque;

  public Challenge(String scheme, String realm) {
    this.scheme = scheme;
    this.realm = realm;
    this.nonce = null;
    this.stale = null;
    this.qop   = null;
    this.opaque = null;
  }

  public Challenge(String scheme, String realm, String nonce, String stale,
        String qop, String opaque) {
    this.scheme = scheme;
    this.realm = realm;
    this.nonce = nonce;
    this.stale = stale;
    this.qop = qop;
    this.opaque = opaque;
  }

  /** Returns the authentication scheme, like {@code Basic}. */
  public String getScheme() {
    return scheme;
  }

  /** Returns the protection space. */
  public String getRealm() {
    if (System.getProperty("digest.gbarealm") != null &&
            !System.getProperty("digest.gbarealm").isEmpty()) {
        return System.getProperty("digest.gbarealm");
    }
    return realm;
  }

  /** Returns the nonce. */
  public String getNonce() {
    return nonce;
  }

  /** Returns the stale. */
  public String getStale() {
    return stale;
  }

  /** Returns the qop. */
  public String getQop() {
    return qop;
  }

  /** Returns the opaque. */
  public String getOpaque() {
    return opaque;
  }

  @Override public boolean equals(Object o) {
    return o instanceof Challenge
        && equal(scheme, ((Challenge) o).scheme)
        && equal(realm, ((Challenge) o).realm);
  }

  @Override public int hashCode() {
    int result = 29;
    result = 31 * result + (realm != null ? realm.hashCode() : 0);
    result = 31 * result + (scheme != null ? scheme.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return scheme + " realm=\"" + realm + "\"";
  }
}
