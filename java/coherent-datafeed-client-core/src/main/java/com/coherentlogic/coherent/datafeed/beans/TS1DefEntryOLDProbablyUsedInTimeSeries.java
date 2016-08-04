package com.coherentlogic.coherent.datafeed.beans;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Def;

/**
 * An entry that helps track requests for
 * {@link TS1Def} information.
 *
 * @todo This class replaces the TimeSeriesEntries.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefEntryOLDProbablyUsedInTimeSeries implements Serializable {

//  We don't need this as the holder will be added to the map using whatever
//  handle the query returns. The holder has a map of TimeSeriesEntry (CTS)
//  instances which are retrieved using the handle as a key, so we don't need
//  to track this. In fact we can query TR with all of the remaining RICs and
//  we should be able to find where the corresponding CTS is without issue.
//
//  private Handle currentHandle = null;

    private static final long serialVersionUID = -7066164170704371114L;

    private final Map<Handle, TimeSeriesEntry>
      multipartCachedTimeSeriesMap;

  /**
   * Do we need this?
   */
  private List<String> ricList = new ArrayList<String> ();

  private final int period;

  private boolean ricsHaveBeenAdded = false;

  public TS1DefEntryOLDProbablyUsedInTimeSeries(int period) {
      this (
          period,
          new HashMap<Handle, TimeSeriesEntry> (),
          new ArrayList<String> ()
      );
  }

  public TS1DefEntryOLDProbablyUsedInTimeSeries(
      int period,
      Map<Handle, TimeSeriesEntry> multipartCachedTimeSeriesMap,
      List<String> rics
  ) {
      this.period = period;
      this.multipartCachedTimeSeriesMap = multipartCachedTimeSeriesMap;
      this.ricList = rics;
  }

  public Map<Handle, TimeSeriesEntry> getMultipartCachedTimeSeriesMap() {
      return multipartCachedTimeSeriesMap;
  }

  public void putMultipartCachedTimeSeries(
      Handle handle,
      TimeSeriesEntry timeSeriesEntry
  ) {
      multipartCachedTimeSeriesMap.put(handle, timeSeriesEntry);
  }

  public TimeSeriesEntry getMultipartCachedTimeSeries (
      Handle handle
  ) {
      return multipartCachedTimeSeriesMap.get (handle);
  }

  public void addRics (String... rics) {

      assertNotNull ("rics", rics);

      List<String> results = new ArrayList<String> ();

      for (String ric : rics)
          ricList.add(ric);

      addRics(results);
  }

  public void addRics (Collection<String> rics) {
      ricList.addAll(rics);
      ricsHaveBeenAdded = true;
  }

  /**
   * Method removes the first RIC and returns this value.
   *
   * When this method returns null, there are no further RICs to be processed.
   */
  public String nextRic () {

      int last = ricList.size() - 1;

      String nextRic = ricList.remove(last);

      return nextRic;
  }

  public int getRicListSize () {
      return ricList.size();
  }

  public boolean isRicListEmpty () {
      return ricList.isEmpty();
  }

  public int getPeriod() {
      return period;
  }

  public boolean ricsHaveBeenAdded () {
      return ricsHaveBeenAdded;
  }
}
