/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */

package com.codename1.calendar;

import com.codename1.calendar.impl.CalendarNativeInterface;
import com.codename1.system.NativeInterface;
import com.codename1.system.NativeLookup;

/**
 * This class will represent the user visible portable API
 *
 * @author Shai Almog
 * @author Kapila de Lanerolle
 */
public class DeviceCalendar implements CalendarInterface {

   public boolean hasPermissions() {
      NativeInterface impl = NativeLookup.create(CalendarNativeInterface.class);

      return impl != null && impl.isSupported() && ((CalendarNativeInterface) impl).hasPermissions();
   }

   public int getCalendarCount() {
	   return hasPermissions() ? ((CalendarNativeInterface)NativeLookup.create(CalendarNativeInterface.class)).getCalendarCount() : 0;
   }
   
   public String getCalendarName(int offset) {
	   return hasPermissions() ? ((CalendarNativeInterface)NativeLookup.create(CalendarNativeInterface.class)).getCalendarName(offset) : null;
   }

   public String openCalendar(String calendarName, boolean createIfNotExists) {
      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).openCalendar(calendarName, createIfNotExists) : null;
   }

   public String saveEvent(String calendarID, String eventID, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, boolean taskOnly, String notes, String location, String reminders) {
      if (calendarID == null || calendarID.isEmpty())
         throw new IllegalArgumentException("calendarID required");
      
      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).saveEvent(calendarID, eventID, title, startTimeStamp, endTimeStamp, allDayEvent, taskOnly, notes, location, reminders) : null;
   }

   public boolean removeEvent(String calendarID, String eventID) {
	   if (calendarID == null || calendarID.isEmpty())
		   throw new IllegalArgumentException("calendarID required");
	   if (eventID == null || eventID.isEmpty())
		   throw new IllegalArgumentException("eventID required");

	   return hasPermissions() && ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).removeEvent(calendarID, eventID);
   }

   public String getEventByID(String calendarID, String eventID) {
	   if (calendarID == null || calendarID.isEmpty())
		   throw new IllegalArgumentException("calendarID required");
      if (eventID == null || eventID.isEmpty())
         throw new IllegalArgumentException("eventID required");

      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).getEventByID(calendarID, eventID) : null;
   }

   public String getEvents(String calendarID, long startTimeStamp, long endTimeStamp) {
      if (calendarID == null || calendarID.isEmpty())
         throw new IllegalArgumentException("calendarID required");

      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).getEvents(calendarID, startTimeStamp, endTimeStamp) : null;
   }

   public void registerForEventNotifications() {
      if (hasPermissions())
         ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).registerForEventNotifications();
   }

   public void deregisterForEventNotifications() {
      if (hasPermissions())
         ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).deregisterForEventNotifications();
   }

  
}
