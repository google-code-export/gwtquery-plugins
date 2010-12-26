package com.google.gwt.user.cellview.client;
/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * CellBasedWidgetImplSafari implementation based on revision rr=9220 :
 * http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/src/com/google/gwt/user/cellview/client/CellBasedWidgetImplSafari.java?spec=svn9203&r=9220
 * 
 * Webkit specified Impl used by cell based widgets.
 * 
 * @deprecated Use {@link com.google.gwt.user.cellview.client.CellWidgetImpleSafari} instead.
 *             This class will be removed in future release.
 */
@Deprecated
public class CellBasedWidgetImplSafari extends CellBasedWidgetImplStandard {

  @Override
  public void resetFocus(ScheduledCommand command) {
    // Webkit will not focus an element that was created in this event loop.
    Scheduler.get().scheduleDeferred(command);
  }
}
