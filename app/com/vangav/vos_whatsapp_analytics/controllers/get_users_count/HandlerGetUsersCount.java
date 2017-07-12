/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.vos_whatsapp_analytics.controllers.get_users_count;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.vangav.backend.cassandra.Cassandra;
import com.vangav.backend.cassandra.formatting.CalendarFormatterInl;
import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.metrics.time.CalendarAndDateOperationsInl;
import com.vangav.backend.play_framework.param.ParamParsersInl;
import com.vangav.backend.play_framework.request.Request;
import com.vangav.backend.play_framework.request.RequestJsonBody;
import com.vangav.backend.play_framework.request.response.ResponseBody;
import com.vangav.vos_whatsapp_analytics.cassandra_keyspaces.wa_analysis.UsersCount;
import com.vangav.vos_whatsapp_analytics.controllers.CommonPlayHandler;
import com.vangav.vos_whatsapp_analytics.controllers.get_users_count.response_json.ResponseDayUsersCount;

/**
 * GENERATED using ControllersGeneratorMain.java
 */
/**
 * HandlerGetUsersCount
 *   handles request-to-response processing
 *   also handles after response processing (if any)
 * */
public class HandlerGetUsersCount extends CommonPlayHandler {

  private static final String kName = "GetUsersCount";

  @Override
  protected String getName () {

    return kName;
  }

  @Override
  protected RequestJsonBody getRequestJson () {

    return new RequestGetUsersCount();
  }

  @Override
  protected ResponseBody getResponseBody () {

    return new ResponseGetUsersCount();
  }
  
  private static final DateFormat kDateFormatDay =
    new SimpleDateFormat("dd/MM/yyyy");

  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestGetUsersCount requestGetUsersCount =
      (RequestGetUsersCount)request.getRequestJsonBody();
    
    // parse from/to dates into calendar Objects
   Calendar fromCalendar =
     ParamParsersInl.parseCalendar(requestGetUsersCount.from_date);
   Calendar toCalendar =
     ParamParsersInl.parseCalendar(requestGetUsersCount.to_date);
   
   // get all calendar dates from-to
   ArrayList<Calendar> calendarsRange =
     CalendarAndDateOperationsInl.getCalendarsFromTo(
       fromCalendar,
       toCalendar);
   
   // to-date is smaller than from-date?!
   if (calendarsRange.isEmpty() == true) {
     
     throw new BadRequestException(
       401,
       1,
       "No dates-range between from-date ["
         + requestGetUsersCount.from_date
         + "] and to-date ["
         + requestGetUsersCount.to_date
         + "]",
       ExceptionClass.INVALID);
   }
   
   // format into cassandra-calendars
   ArrayList<String> cassandraCalendars =
     CalendarFormatterInl.concatCalendarsFields(
       calendarsRange,
       Calendar.YEAR,
       Calendar.MONTH,
       Calendar.DAY_OF_MONTH);
   
   // for storing bound statements
   ArrayList<BoundStatement> boundStatements =
     new ArrayList<BoundStatement>();
   
   // get data from cassandra
   for (int i = 0; i < cassandraCalendars.size(); i ++) {
     
     boundStatements.add(
       UsersCount.i().getBoundStatementSelect(
         cassandraCalendars.get(i) ) );
   }
   
   // execute bound statements
   ArrayList<ResultSet> resultSets =
     Cassandra.i().executeSync(
       boundStatements.toArray(new BoundStatement[0] ) );
   
   long totalCount = 0L;
   long currDayCount;
   ArrayList<ResponseDayUsersCount> responseDayUsersCountList =
     new ArrayList<ResponseDayUsersCount>();
   
   // for each day
   for (int i = 0; i < resultSets.size(); i ++) {
     
     // no-count day?
     if (resultSets.get(i).isExhausted() == true) {
       
       continue;
     }
     
     // get day's count
     currDayCount =
       resultSets.get(i).one().getLong(UsersCount.kCounterValueColumnName);
     
     totalCount += currDayCount;
     
     // add day's count
     responseDayUsersCountList.add(
       new ResponseDayUsersCount(
         kDateFormatDay.format(
           CalendarAndDateOperationsInl.getDateFromCalendar(
             calendarsRange.get(i) ) ),
         currDayCount) );
   }
   
   // set response
   ((ResponseGetUsersCount)request.getResponseBody() ).set(
     totalCount,
     responseDayUsersCountList.toArray(new ResponseDayUsersCount[0] ) );
  }
}
