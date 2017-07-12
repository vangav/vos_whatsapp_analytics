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

package com.vangav.vos_whatsapp_analytics.cassandra_keyspaces.wa_analysis;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.vangav.backend.cassandra.keyspaces.Query;
import com.vangav.backend.cassandra.keyspaces.Table;
import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;

/**
 * GENERATED using JavaClientGeneratorMain.java
 */
/**
 * UsersCount represents
 *   Table [users_count]
 *   in Keyspace [wa_analysis]
 * 
 * Name: users_count
 * Description:
 *   counts new users per-day 
 * 
 * Columns:
 *   year_month_day : varchar
 *   counter_value : counter

 * Partition Keys: year_month_day
 * Secondary Keys: 
 * Caching: ALL
 * Order By:

 * Queries:
 *   - Name: select
 *   Description:
 *     selects the number of signed up users on a day 
 *   Prepared Statement:
 *     SELECT counter_value FROM wa_analysis.users_count WHERE 
 *     year_month_day = :year_month_day; 
 * */
public class UsersCount extends Table {

  private static final String kKeySpaceName =
    "wa_analysis";
  private static final String kTableName =
    "users_count";

  public static final String kYearMonthDayColumnName =
    "year_month_day";
  public static final String kCounterValueColumnName =
    "counter_value";

  /**
   * Query:
   * Name: select
   * Description:
   *   selects the number of signed up users on a day 
   * Prepared Statement:
   *   SELECT counter_value FROM wa_analysis.users_count WHERE 
   *   year_month_day = :year_month_day; 
   */
  private static final String kSelectName =
    "select";
  private static final String kSelectDescription =
    "selects the number of signed up users on a day ";
  private static final String kSelectPreparedStatement =
    "SELECT counter_value FROM wa_analysis.users_count WHERE year_month_day "
    + "= :year_month_day; ";

  /**
   * Constructor UsersCount
   * @return new UsersCount Object
   * @throws Exception
   */
  private UsersCount () throws Exception {

    super (
      kKeySpaceName,
      kTableName,
      new Query (
        kSelectDescription,
        kSelectName,
        kSelectPreparedStatement));
  }

  private static UsersCount instance = null;

  /**
   * loadTable
   * OPTIONAL method
   * instance is created either upon calling this method or upon the first call
   *   to singleton instance method i
   * this method is useful for loading upon program start instead of loading
   *   it upon the first use since there's a small time overhead for loading
   *   since all queries are prepared synchronously in a blocking network
   *   operation with Cassandra's server
   * @throws Exception
   */
  public static void loadTable () throws Exception {

    if (instance == null) {

      instance = new UsersCount();
    }
  }

  /**
   * i
   * @return singleton static instance of UsersCount
   * @throws Exception
   */
  public static UsersCount i () throws Exception {

    if (instance == null) {

      instance = new UsersCount();
    }

    return instance;
  }

  // Query: Select
  // Description:
  //   selects the number of signed up users on a day 
  // Parepared Statement:
  //   SELECT counter_value FROM wa_analysis.users_count WHERE 
  //   year_month_day = :year_month_day; 

  /**
   * getQuerySelect
   * @return Select Query in the form of
   *           a Query Object
   * @throws Exception
   */
  public Query getQuerySelect (
    ) throws Exception {

    return this.getQuery(kSelectName);
  }

  /**
   * getQueryDispatchableSelect
   * @param yearmonthday
   * @return Select Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableSelect (
    Object yearmonthday) throws Exception {

    return
      this.getQueryDispatchable(
        kSelectName,
        yearmonthday);
  }

  /**
   * getBoundStatementSelect
   * @param yearmonthday
   * @return Select Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementSelect (
    Object yearmonthday) throws Exception {

    return
      this.getQuery(kSelectName).getBoundStatement(
        yearmonthday);
  }

  /**
   * executeAsyncSelect
   * executes Select Query asynchronously
   * @param yearmonthday
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncSelect (
    Object yearmonthday) throws Exception {

    return
      this.getQuery(kSelectName).executeAsync(
        yearmonthday);
  }

  /**
   * executeSyncSelect
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes Select Query synchronously
   * @param yearmonthday
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncSelect (
    Object yearmonthday) throws Exception {

    return
      this.getQuery(kSelectName).executeSync(
        yearmonthday);
  }

}
