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

package com.codename1.calendar.impl;

import com.codename1.system.NativeInterface;

/**
 *
 * @author Kapila de Lanerolle
 */
public interface CalendarNativeInterface extends NativeInterface {
	/**
	 * @return If we have permissions for calendar modifications
	 * @author kdelanerolle (Dec 11, 2013)
	 */
	boolean hasPermissions();
	
	/**
	 * AddsEdits an event in default calendar
	 * @param eventIdentifier - Event Identifier. Pass null for new Events
	 * @param title - Title of the Calendar Event
	 * @param startTimeStamp - Event starting timestamp (unix time)
	 * @param endTimeStamp - Event ending timestamp (unix time)
	 * @param allDayEvent - The event is an all day event
	 * @param taskOnly - Task Only. No time associated with this.
	 * @param notes - Any notes for the event
	 * @param location - Location of the event
	 * @param reminders - alarm offsets (in seconds) in CSV format. Pass null for no alarms
	 * @return Unique event identifier for the event that's created. Null in the case of failure
	 * @author kdelanerolle (Dec 10, 2013)
	 */
	String saveEvent(String eventIdentifier, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, boolean taskOnly, String notes, String location, String reminders);
	
	/**
	 * Removes an event of given eventIdendifier from default calendar
	 * @param eventIdentifier
	 * @return If removal successful
	 * @author kdelanerolle (Dec 11, 2013)
	 */
	boolean removeEvent(String eventIdentifier);
	
	/**
	 * @param eventIdentifier - Event Identifier
	 * @return XML String of event details. Null if not found.
	 * @author kdelanerolle (Dec 13, 2013)
	 */
	String getEventByIdentifier(String eventIdentifier);
	
	/**
	 * Returns all events in the calendar between startTimeStamp and endTimeStamp 
	 * @param startTimeStamp
	 * @param endTimeStamp
	 * @return XML string of all events found
	 * @author kdelanerolle (Dec 19, 2013)
	 */
	String getEvents(long startTimeStamp, long endTimeStamp);
	
	
	/**
	 * The following are for registering/deregistering for receiving callbacks when calendar events are modified 
	 * @author kdelanerolle (Dec 19, 2013)
	 */
	void registerForEventNotifications();
	void deregisterForEventNotifications();
}
