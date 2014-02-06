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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;

import com.codename1.calendar.impl.CalendarNativeInterface;
import com.codename1.io.CharArrayReader;
import com.codename1.system.NativeInterface;
import com.codename1.system.NativeLookup;
import com.codename1.xml.Element;
import com.codename1.xml.XMLParser;

/**
 * This class will represent the user visible portable API
 *
 * @author Shai Almog
 * @author Kapila de Lanerolle
 * @author Andreas Heydler
 */
public final class DeviceCalendar implements CalendarInterface {
   
   private static final CalendarInterface INSTANCE = new DeviceCalendar();
   
   public static CalendarInterface getInstance() {
      return INSTANCE;
   }
   
   //
   // user API
   //

   public boolean hasPermissions() {
      NativeInterface impl = NativeLookup.create(CalendarNativeInterface.class);

      return impl != null && impl.isSupported() && ((CalendarNativeInterface) impl).hasPermissions();
   }

   public Collection<String> getCalendars() {
      if (hasPermissions()) {
         CalendarNativeInterface impl = (CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class);
         int cnt = impl.getCalendarCount();         
         Collection<String> list = new LinkedList<String>();
         
         for (int i = 0; i < cnt; i++)
            list.add(impl.getCalendarName(i));
         
         return list;         
      }
      
      return null;
   }

   public String openCalendar(String calendarName, boolean createIfNotExists) {
      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).openCalendar(calendarName, createIfNotExists) : null;
   }

   public String saveEvent(String calendarID, String eventID, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, String notes, String location, Collection<Integer> reminders) {
      if (calendarID == null || calendarID.length() == 0)
         throw new IllegalArgumentException("calendarID required");

      String sReminders;
            
      if (reminders != null && reminders.size() > 0) {
         sReminders = "";
         
         for (int reminder : reminders)
            sReminders += reminder + ",";

         sReminders = sReminders.substring(0, sReminders.length() - 1);
      }
      else
         sReminders = null;
      
      return hasPermissions() ? ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).saveEvent(calendarID, eventID, title, startTimeStamp, endTimeStamp, allDayEvent, false, notes, location, sReminders) : null;
   }

   public boolean removeEvent(String calendarID, String eventID) {
	   if (calendarID == null || calendarID.length() == 0)
		   throw new IllegalArgumentException("calendarID required");

	   if (eventID == null || eventID.length() == 0)
		   throw new IllegalArgumentException("eventID required");

	   return hasPermissions() && ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).removeEvent(calendarID, eventID);
   }

   public EventInfo getEventByID(String calendarID, String eventID) {
	   if (calendarID == null || calendarID.length() == 0)
		   throw new IllegalArgumentException("calendarID required");

      if (eventID == null || eventID.length() == 0)
         throw new IllegalArgumentException("eventID required");

      if (hasPermissions()) {
         String      xml = ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).getEventByID(calendarID, eventID);
         Element element = new XMLParser().parse(new CharArrayReader(xml.toCharArray()));
         
//         Log.p("event XML " + xml);
//         Log.p("parsed " + element);
         
         return new EventInfo(findElement(element, "response", "event"));         
      }
      
      return null;
   }

   public Collection<EventInfo> getEvents(String calendarID, long startTimeStamp, long endTimeStamp) {
      if (calendarID == null || calendarID.length() == 0)
         throw new IllegalArgumentException("calendarID required");

      if (hasPermissions()) {
         String      xml = ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).getEvents(calendarID, startTimeStamp, endTimeStamp);
         Element element = new XMLParser().parse(new CharArrayReader(xml.toCharArray()));

//         Log.p("events XML " + xml);
//         Log.p("parsed " + element);
         
         element = findElement(element, "response", "eventList"); 
         Collection<EventInfo> col = new ArrayList<EventInfo>();         
         
         if (element != null) {
            Vector<Element> events = element.getChildrenByTagName("event");
            
            if (events != null) {
               Enumeration<Element> enumeration = events.elements();
               
               while (enumeration.hasMoreElements())
                  col.add(new EventInfo(enumeration.nextElement()));
            }
//            else
//               Log.p("no events");
         }
//         else
//            Log.p("no eventList");
            
         return col;         
      }
      
      return null;
   }
   
   private static Element findElement(Element element, String... tags) {
      if (element != null) {
         for (String tag : tags) {
            element = element.getFirstChildByTagName(tag);
            
//            Log.p("child " + element);
            
            if (element == null)
               break;            
         }
      }
      
      return element;
   }

//   public void registerForEventNotifications() {
//      if (hasPermissions())
//         ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).registerForEventNotifications();
//   }
//
//   public void deregisterForEventNotifications() {
//      if (hasPermissions())
//         ((CalendarNativeInterface) NativeLookup.create(CalendarNativeInterface.class)).deregisterForEventNotifications();
//   }

  
   public static class EventInfo {
      private final String  id;    
      private final String  title;    
      private final String  description;    
      private final String  location;
      private final long    startTimeStamp;
      private final long    endTimeStamp;
      private final boolean allDayEvent;
      private final int[]   reminders;

      public EventInfo(Element element) {
//         Log.p("EventInfo(" + element + ")");
         
         if (element == null)
            throw new IllegalArgumentException("element cannot be null");
         
         id             = element.getFirstChildByTagName("id").         getChildAt(0).getText();
         title          = element.getFirstChildByTagName("title").      getChildAt(0).getText();
         description    = element.getFirstChildByTagName("description").getChildAt(0).getText();
         location       = element.getFirstChildByTagName("location").   getChildAt(0).getText();
         startTimeStamp = Long.parseLong(element.getFirstChildByTagName("startTimeStamp").getChildAt(0).getText());
         endTimeStamp   = Long.parseLong(element.getFirstChildByTagName("endTimeStamp").  getChildAt(0).getText());
         allDayEvent    = "true".equals (element.getFirstChildByTagName("allDayEvent").   getChildAt(0).getText());
         
         Element rems = element.getFirstChildByTagName("reminders");
         
         if (rems != null && rems.getNumChildren() > 0) {
            Vector<Element> seconds = rems.getChildrenByTagName("reminderOffset");
            
            reminders = new int[seconds != null ? seconds.size() : 0];

            for (int i = 0; i < reminders.length; i++) 
               reminders[i] = Integer.parseInt(seconds.elementAt(i).getChildAt(0).getText());            
         }
         else
            reminders = new int[0];
      }

      public String getID() {
         return id;
      }

      public String getTitle() {
         return title;
      }

      public String getDescription() {
         return description;
      }

      public String getLocation() {
         return location;
      }

      public long getStartTimeStamp() {
         return startTimeStamp;
      }

      public long getEndTimeStamp() {
         return endTimeStamp;
      }

      public boolean isAllDayEvent() {
         return allDayEvent;
      }

      public int[] getReminders() {
         return reminders;
      }

      @Override
      public String toString() {
         return "EventInfo{" +
                  "id='"            + id             + '\'' +
                ", title='"         + title          + '\'' +
                ", description='"   + description    + '\'' +
                ", location='"      + location       + '\'' +
                ", startTimeStamp=" + startTimeStamp +
                ", endTimeStamp="   + endTimeStamp   +
                ", allDayEvent="    + allDayEvent    +
                ", reminders="      + Arrays.toString(reminders) +
                '}';
      }
   }
}
