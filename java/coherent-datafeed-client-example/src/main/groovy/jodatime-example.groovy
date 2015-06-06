@Grab(group='joda-time', module='joda-time', version='2.1')
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.DateTime

String patterns = "yyyy-MMM-dd";

String example = "2012-NOV-17"

DateTimeFormatter formatter = DateTimeFormat.forPattern(patterns);
DateTime dateTime=formatter.parseDateTime(example);