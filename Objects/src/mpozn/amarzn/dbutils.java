package mpozn.amarzn;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbutils {
private static dbutils mostCurrent = new dbutils();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _db_real = "";
public static String _db_integer = "";
public static String _db_blob = "";
public static String _db_text = "";
public static String _htmlcss = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public mpozn.amarzn.main _main = null;
public mpozn.amarzn.starter _starter = null;
public mpozn.amarzn.actsal _actsal = null;
public mpozn.amarzn.actsalchild _actsalchild = null;
public mpozn.amarzn.actsalviewer _actsalviewer = null;
public mpozn.amarzn.actsearch _actsearch = null;
public mpozn.amarzn.actotherviewer _actotherviewer = null;
public static String  _copydbfromassets(anywheresoftware.b4a.BA _ba,String _filename) throws Exception{
String _targetdir = "";
 //BA.debugLineNum = 30;BA.debugLine="Sub CopyDBFromAssets (FileName As String) As Strin";
 //BA.debugLineNum = 31;BA.debugLine="Dim TargetDir As String";
_targetdir = "";
 //BA.debugLineNum = 32;BA.debugLine="If File.ExternalWritable Then TargetDir = File.Di";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()) { 
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal();}
else {
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirInternal();};
 //BA.debugLineNum = 34;BA.debugLine="If File.Exists(TargetDir, FileName) = False Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_targetdir,_filename)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 35;BA.debugLine="File.Copy(File.DirAssets, FileName, TargetDir, F";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_filename,_targetdir,_filename);
 }else if(anywheresoftware.b4a.keywords.Common.File.Exists(_targetdir,_filename)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 37;BA.debugLine="File.Copy(File.DirAssets, FileName, TargetDir, F";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_filename,_targetdir,_filename);
 };
 //BA.debugLineNum = 40;BA.debugLine="Return TargetDir";
if (true) return _targetdir;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _createtable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fieldsandtypes,String _primarykey) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
String _field = "";
String _ftype = "";
String _query = "";
 //BA.debugLineNum = 47;BA.debugLine="Sub CreateTable(SQL As SQL, TableName As String, F";
 //BA.debugLineNum = 48;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 49;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 50;BA.debugLine="sb.Append(\"(\")";
_sb.Append("(");
 //BA.debugLineNum = 51;BA.debugLine="For i = 0 To FieldsAndTypes.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_fieldsandtypes.getSize()-1);
_i = (int) (0) ;
for (;(step4 > 0 && _i <= limit4) || (step4 < 0 && _i >= limit4) ;_i = ((int)(0 + _i + step4))  ) {
 //BA.debugLineNum = 52;BA.debugLine="Dim field, ftype As String";
_field = "";
_ftype = "";
 //BA.debugLineNum = 53;BA.debugLine="field = FieldsAndTypes.GetKeyAt(i)";
_field = BA.ObjectToString(_fieldsandtypes.GetKeyAt(_i));
 //BA.debugLineNum = 54;BA.debugLine="ftype = FieldsAndTypes.GetValueAt(i)";
_ftype = BA.ObjectToString(_fieldsandtypes.GetValueAt(_i));
 //BA.debugLineNum = 55;BA.debugLine="If i > 0 Then sb.Append(\", \")";
if (_i>0) { 
_sb.Append(", ");};
 //BA.debugLineNum = 56;BA.debugLine="sb.Append(\"[\").Append(field).Append(\"] \").Append";
_sb.Append("[").Append(_field).Append("] ").Append(_ftype);
 //BA.debugLineNum = 57;BA.debugLine="If field = PrimaryKey Then sb.Append(\" PRIMARY K";
if ((_field).equals(_primarykey)) { 
_sb.Append(" PRIMARY KEY");};
 }
};
 //BA.debugLineNum = 59;BA.debugLine="sb.Append(\")\")";
_sb.Append(")");
 //BA.debugLineNum = 60;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 61;BA.debugLine="query = \"CREATE TABLE IF NOT EXISTS [\" & TableNam";
_query = "CREATE TABLE IF NOT EXISTS ["+_tablename+"] "+_sb.ToString();
 //BA.debugLineNum = 62;BA.debugLine="Log(\"CreateTable: \" & query)";
anywheresoftware.b4a.keywords.Common.Log("CreateTable: "+_query);
 //BA.debugLineNum = 63;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _deleterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 396;BA.debugLine="Sub DeleteRecord(SQL As SQL, TableName As String,";
 //BA.debugLineNum = 397;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 398;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 399;BA.debugLine="sb.Append(\"DELETE FROM [\").Append(TableName).A";
_sb.Append("DELETE FROM [").Append(_tablename).Append("] WHERE ");
 //BA.debugLineNum = 400;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 401;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 //BA.debugLineNum = 402;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 404;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 405;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 406;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;(step10 > 0 && _i <= limit10) || (step10 < 0 && _i >= limit10) ;_i = ((int)(0 + _i + step10))  ) {
 //BA.debugLineNum = 407;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
if (_i>0) { 
_sb.Append(" AND ");};
 //BA.debugLineNum = 408;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.Get";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 409;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 411;BA.debugLine="Log(\"DeleteRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.Log("DeleteRecord: "+_sb.ToString());
 //BA.debugLineNum = 412;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 413;BA.debugLine="End Sub";
return "";
}
public static String  _droptable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
String _query = "";
 //BA.debugLineNum = 67;BA.debugLine="Sub DropTable(SQL As SQL, TableName As String)";
 //BA.debugLineNum = 68;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 69;BA.debugLine="query = \"DROP TABLE IF EXISTS [\" & TableName & \"]";
_query = "DROP TABLE IF EXISTS ["+_tablename+"]";
 //BA.debugLineNum = 70;BA.debugLine="Log(\"DropTable: \" & query)";
anywheresoftware.b4a.keywords.Common.Log("DropTable: "+_query);
 //BA.debugLineNum = 71;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _executehtml(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,boolean _clickable) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
int _row = 0;
 //BA.debugLineNum = 315;BA.debugLine="Sub ExecuteHtml(SQL As SQL, Query As String, Strin";
 //BA.debugLineNum = 316;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 317;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 318;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 319;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 321;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 323;BA.debugLine="Log(\"ExecuteHtml: \" & Query)";
anywheresoftware.b4a.keywords.Common.Log("ExecuteHtml: "+_query);
 //BA.debugLineNum = 324;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 325;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 326;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 327;BA.debugLine="sb.Append(\"<html><body>\").Append(CRLF)";
_sb.Append("<html><body>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 328;BA.debugLine="sb.Append(\"<style type='text/css'>\").Append(HtmlC";
_sb.Append("<style type='text/css'>").Append(_htmlcss).Append("</style>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 329;BA.debugLine="sb.Append(\"<table><tr>\").Append(CRLF)";
_sb.Append("<table><tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 330;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step15 = 1;
final int limit15 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;(step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15) ;_i = ((int)(0 + _i + step15))  ) {
 //BA.debugLineNum = 331;BA.debugLine="sb.Append(\"<th>\").Append(cur.GetColumnName(i)).A";
_sb.Append("<th>").Append(_cur.GetColumnName(_i)).Append("</th>");
 }
};
 //BA.debugLineNum = 342;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 343;BA.debugLine="For row = 0 To Limit - 1";
{
final int step19 = 1;
final int limit19 = (int) (_limit-1);
_row = (int) (0) ;
for (;(step19 > 0 && _row <= limit19) || (step19 < 0 && _row >= limit19) ;_row = ((int)(0 + _row + step19))  ) {
 //BA.debugLineNum = 344;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 345;BA.debugLine="If row Mod 2 = 0 Then";
if (_row%2==0) { 
 //BA.debugLineNum = 346;BA.debugLine="sb.Append(\"<tr>\")";
_sb.Append("<tr>");
 }else {
 //BA.debugLineNum = 348;BA.debugLine="sb.Append(\"<tr class='odd'>\")";
_sb.Append("<tr class='odd'>");
 };
 //BA.debugLineNum = 350;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step26 = 1;
final int limit26 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;(step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26) ;_i = ((int)(0 + _i + step26))  ) {
 //BA.debugLineNum = 351;BA.debugLine="sb.Append(\"<td>\")";
_sb.Append("<td>");
 //BA.debugLineNum = 352;BA.debugLine="If Clickable Then";
if (_clickable) { 
 //BA.debugLineNum = 353;BA.debugLine="sb.Append(\"<a href='http://\").Append(i).Append";
_sb.Append("<a href='http://").Append(BA.NumberToString(_i)).Append(".");
 //BA.debugLineNum = 354;BA.debugLine="sb.Append(row)";
_sb.Append(BA.NumberToString(_row));
 //BA.debugLineNum = 355;BA.debugLine="sb.Append(\".com'>\").Append(cur.GetString2(i)).";
_sb.Append(".com'>").Append(_cur.GetString2(_i)).Append("</a>");
 }else {
 //BA.debugLineNum = 357;BA.debugLine="sb.Append(cur.GetString2(i))";
_sb.Append(_cur.GetString2(_i));
 };
 //BA.debugLineNum = 359;BA.debugLine="sb.Append(\"</td>\")";
_sb.Append("</td>");
 }
};
 //BA.debugLineNum = 361;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 }
};
 //BA.debugLineNum = 363;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 364;BA.debugLine="sb.Append(\"</table></body></html>\")";
_sb.Append("</table></body></html>");
 //BA.debugLineNum = 365;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _executejson(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.collections.List _dbtypes) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
int _row = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 276;BA.debugLine="Sub ExecuteJSON (SQL As SQL, Query As String, Stri";
 //BA.debugLineNum = 277;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 278;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 279;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 280;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 282;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 284;BA.debugLine="Log(\"ExecuteJSON: \" & Query)";
anywheresoftware.b4a.keywords.Common.Log("ExecuteJSON: "+_query);
 //BA.debugLineNum = 285;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 286;BA.debugLine="table.Initialize";
_table.Initialize();
 //BA.debugLineNum = 287;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 288;BA.debugLine="For row = 0 To Limit - 1";
{
final int step12 = 1;
final int limit12 = (int) (_limit-1);
_row = (int) (0) ;
for (;(step12 > 0 && _row <= limit12) || (step12 < 0 && _row >= limit12) ;_row = ((int)(0 + _row + step12))  ) {
 //BA.debugLineNum = 289;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 290;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 291;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 292;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step16 = 1;
final int limit16 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;(step16 > 0 && _i <= limit16) || (step16 < 0 && _i >= limit16) ;_i = ((int)(0 + _i + step16))  ) {
 //BA.debugLineNum = 293;BA.debugLine="Select DBTypes.Get(i)";
switch (BA.switchObjectToInt(_dbtypes.Get(_i),(Object)(_db_text),(Object)(_db_integer),(Object)(_db_real))) {
case 0: {
 //BA.debugLineNum = 295;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetString2(i)";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetString2(_i)));
 break; }
case 1: {
 //BA.debugLineNum = 297;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetLong2(i))";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetLong2(_i)));
 break; }
case 2: {
 //BA.debugLineNum = 299;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetDouble2(i)";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetDouble2(_i)));
 break; }
default: {
 //BA.debugLineNum = 301;BA.debugLine="Log(\"Invalid type: \" & DBTypes.Get(i))";
anywheresoftware.b4a.keywords.Common.Log("Invalid type: "+BA.ObjectToString(_dbtypes.Get(_i)));
 break; }
}
;
 }
};
 //BA.debugLineNum = 304;BA.debugLine="table.Add(m)";
_table.Add((Object)(_m.getObject()));
 }
};
 //BA.debugLineNum = 306;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 307;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 308;BA.debugLine="root.Initialize";
_root.Initialize();
 //BA.debugLineNum = 309;BA.debugLine="root.Put(\"root\", table)";
_root.Put((Object)("root"),(Object)(_table.getObject()));
 //BA.debugLineNum = 310;BA.debugLine="Return root";
if (true) return _root;
 //BA.debugLineNum = 311;BA.debugLine="End Sub";
return null;
}
public static String  _executelistview(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.ListViewWrapper _listview1,boolean _twolines) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
 //BA.debugLineNum = 251;BA.debugLine="Sub ExecuteListView(SQL As SQL, Query As String, S";
 //BA.debugLineNum = 253;BA.debugLine="ListView1.Clear";
_listview1.Clear();
 //BA.debugLineNum = 254;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 255;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs";
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);
 //BA.debugLineNum = 256;BA.debugLine="Dim Cols() As String";
_cols = new String[(int) (0)];
java.util.Arrays.fill(_cols,"");
 //BA.debugLineNum = 257;BA.debugLine="For i = 0 To Table.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_table.getSize()-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 258;BA.debugLine="Cols = Table.Get(i)";
_cols = (String[])(_table.Get(_i));
 //BA.debugLineNum = 259;BA.debugLine="If TwoLines Then";
if (_twolines) { 
 //BA.debugLineNum = 260;BA.debugLine="ListView1.AddTwoLines2(Cols(0), Cols(1), Cols)";
_listview1.AddTwoLines2(BA.ObjectToCharSequence(_cols[(int) (0)]),BA.ObjectToCharSequence(_cols[(int) (1)]),(Object)(_cols));
 }else {
 //BA.debugLineNum = 262;BA.debugLine="ListView1.AddSingleLine2(Cols(0), Cols)";
_listview1.AddSingleLine2(BA.ObjectToCharSequence(_cols[(int) (0)]),(Object)(_cols));
 };
 }
};
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _executemap(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.Map _res = null;
int _i = 0;
 //BA.debugLineNum = 213;BA.debugLine="Sub ExecuteMap(SQL As SQL, Query As String, String";
 //BA.debugLineNum = 214;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 215;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 216;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 218;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 220;BA.debugLine="Log(\"ExecuteMap: \" & Query)";
anywheresoftware.b4a.keywords.Common.Log("ExecuteMap: "+_query);
 //BA.debugLineNum = 221;BA.debugLine="If cur.RowCount = 0 Then";
if (_cur.getRowCount()==0) { 
 //BA.debugLineNum = 222;BA.debugLine="Log(\"No records found.\")";
anywheresoftware.b4a.keywords.Common.Log("No records found.");
 //BA.debugLineNum = 223;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 225;BA.debugLine="Dim res As Map";
_res = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 226;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 227;BA.debugLine="cur.Position = 0";
_cur.setPosition((int) (0));
 //BA.debugLineNum = 228;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step15 = 1;
final int limit15 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;(step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15) ;_i = ((int)(0 + _i + step15))  ) {
 //BA.debugLineNum = 229;BA.debugLine="res.Put(cur.GetColumnName(i).ToLowerCase, cur.Ge";
_res.Put((Object)(_cur.GetColumnName(_i).toLowerCase()),(Object)(_cur.GetString2(_i)));
 }
};
 //BA.debugLineNum = 231;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 232;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _executememorytable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.List _table = null;
int _row = 0;
String[] _values = null;
int _col = 0;
 //BA.debugLineNum = 186;BA.debugLine="Sub ExecuteMemoryTable(SQL As SQL, Query As String";
 //BA.debugLineNum = 187;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 188;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 189;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 191;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 193;BA.debugLine="Log(\"ExecuteMemoryTable: \" & Query)";
anywheresoftware.b4a.keywords.Common.Log("ExecuteMemoryTable: "+_query);
 //BA.debugLineNum = 194;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 195;BA.debugLine="table.Initialize";
_table.Initialize();
 //BA.debugLineNum = 196;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 197;BA.debugLine="For row = 0 To Limit - 1";
{
final int step11 = 1;
final int limit11 = (int) (_limit-1);
_row = (int) (0) ;
for (;(step11 > 0 && _row <= limit11) || (step11 < 0 && _row >= limit11) ;_row = ((int)(0 + _row + step11))  ) {
 //BA.debugLineNum = 198;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 199;BA.debugLine="Dim values(cur.ColumnCount) As String";
_values = new String[_cur.getColumnCount()];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 200;BA.debugLine="For col = 0 To cur.ColumnCount - 1";
{
final int step14 = 1;
final int limit14 = (int) (_cur.getColumnCount()-1);
_col = (int) (0) ;
for (;(step14 > 0 && _col <= limit14) || (step14 < 0 && _col >= limit14) ;_col = ((int)(0 + _col + step14))  ) {
 //BA.debugLineNum = 201;BA.debugLine="values(col) = cur.GetString2(col)";
_values[_col] = _cur.GetString2(_col);
 }
};
 //BA.debugLineNum = 203;BA.debugLine="table.Add(values)";
_table.Add((Object)(_values));
 }
};
 //BA.debugLineNum = 205;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 206;BA.debugLine="Return table";
if (true) return _table;
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return null;
}
public static String  _executespinner(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.SpinnerWrapper _spinner1) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
 //BA.debugLineNum = 236;BA.debugLine="Sub ExecuteSpinner(SQL As SQL, Query As String, St";
 //BA.debugLineNum = 237;BA.debugLine="Spinner1.Clear";
_spinner1.Clear();
 //BA.debugLineNum = 238;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 239;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs";
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);
 //BA.debugLineNum = 240;BA.debugLine="Dim Cols() As String";
_cols = new String[(int) (0)];
java.util.Arrays.fill(_cols,"");
 //BA.debugLineNum = 241;BA.debugLine="For i = 0 To Table.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_table.getSize()-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 242;BA.debugLine="Cols = Table.Get(i)";
_cols = (String[])(_table.Get(_i));
 //BA.debugLineNum = 243;BA.debugLine="Spinner1.Add(Cols(0))";
_spinner1.Add(_cols[(int) (0)]);
 }
};
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static int  _getdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql) throws Exception{
int _count = 0;
int _version = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 370;BA.debugLine="Sub GetDBVersion (SQL As SQL) As Int";
 //BA.debugLineNum = 371;BA.debugLine="Dim count, version As Int";
_count = 0;
_version = 0;
 //BA.debugLineNum = 372;BA.debugLine="count = SQL.ExecQuerySingleResult(\"SELECT count(*";
_count = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT count(*) FROM sqlite_master WHERE Type='table' AND name='DBVersion'")));
 //BA.debugLineNum = 373;BA.debugLine="If count > 0 Then";
if (_count>0) { 
 //BA.debugLineNum = 374;BA.debugLine="version = SQL.ExecQuerySingleResult(\"SELECT vers";
_version = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT version FROM DBVersion")));
 }else {
 //BA.debugLineNum = 377;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 378;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 379;BA.debugLine="m.Put(\"version\", DB_INTEGER)";
_m.Put((Object)("version"),(Object)(_db_integer));
 //BA.debugLineNum = 380;BA.debugLine="CreateTable(SQL, \"DBVersion\", m, \"version\")";
_createtable(_ba,_sql,"DBVersion",_m,"version");
 //BA.debugLineNum = 382;BA.debugLine="SQL.ExecNonQuery(\"INSERT INTO DBVersion VALUES (";
_sql.ExecNonQuery("INSERT INTO DBVersion VALUES (1)");
 //BA.debugLineNum = 384;BA.debugLine="version = 1";
_version = (int) (1);
 };
 //BA.debugLineNum = 387;BA.debugLine="Return version";
if (true) return _version;
 //BA.debugLineNum = 388;BA.debugLine="End Sub";
return 0;
}
public static String  _insertmaps(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.List _listofmaps) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _columns = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _values = null;
int _i1 = 0;
anywheresoftware.b4a.objects.collections.List _listofvalues = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i2 = 0;
String _col = "";
Object _value = null;
 //BA.debugLineNum = 78;BA.debugLine="Sub InsertMaps(SQL As SQL, TableName As String, Li";
 //BA.debugLineNum = 79;BA.debugLine="Dim sb, columns, values As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_columns = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_values = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 81;BA.debugLine="If ListOfMaps.Size > 1 And ListOfMaps.Get(0) = Li";
if (_listofmaps.getSize()>1 && (_listofmaps.Get((int) (0))).equals(_listofmaps.Get((int) (1)))) { 
 //BA.debugLineNum = 82;BA.debugLine="Log(\"Same Map found twice in list. Each item in";
anywheresoftware.b4a.keywords.Common.Log("Same Map found twice in list. Each item in the list should include a different map object.");
 //BA.debugLineNum = 83;BA.debugLine="ToastMessageShow(\"Same Map found twice in list.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Same Map found twice in list. Each item in the list should include a different map object."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 84;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 86;BA.debugLine="SQL.BeginTransaction";
_sql.BeginTransaction();
 //BA.debugLineNum = 87;BA.debugLine="Try";
try { //BA.debugLineNum = 88;BA.debugLine="For i1 = 0 To ListOfMaps.Size - 1";
{
final int step9 = 1;
final int limit9 = (int) (_listofmaps.getSize()-1);
_i1 = (int) (0) ;
for (;(step9 > 0 && _i1 <= limit9) || (step9 < 0 && _i1 >= limit9) ;_i1 = ((int)(0 + _i1 + step9))  ) {
 //BA.debugLineNum = 89;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 90;BA.debugLine="columns.Initialize";
_columns.Initialize();
 //BA.debugLineNum = 91;BA.debugLine="values.Initialize";
_values.Initialize();
 //BA.debugLineNum = 92;BA.debugLine="Dim listOfValues As List";
_listofvalues = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 93;BA.debugLine="listOfValues.Initialize";
_listofvalues.Initialize();
 //BA.debugLineNum = 94;BA.debugLine="sb.Append(\"INSERT INTO [\" & TableName & \"] (\")";
_sb.Append("INSERT INTO ["+_tablename+"] (");
 //BA.debugLineNum = 95;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 96;BA.debugLine="m = ListOfMaps.Get(i1)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_listofmaps.Get(_i1)));
 //BA.debugLineNum = 97;BA.debugLine="For i2 = 0 To m.Size - 1";
{
final int step18 = 1;
final int limit18 = (int) (_m.getSize()-1);
_i2 = (int) (0) ;
for (;(step18 > 0 && _i2 <= limit18) || (step18 < 0 && _i2 >= limit18) ;_i2 = ((int)(0 + _i2 + step18))  ) {
 //BA.debugLineNum = 98;BA.debugLine="Dim col As String";
_col = "";
 //BA.debugLineNum = 99;BA.debugLine="Dim value As Object";
_value = new Object();
 //BA.debugLineNum = 100;BA.debugLine="col = m.GetKeyAt(i2)";
_col = BA.ObjectToString(_m.GetKeyAt(_i2));
 //BA.debugLineNum = 101;BA.debugLine="value = m.GetValueAt(i2)";
_value = _m.GetValueAt(_i2);
 //BA.debugLineNum = 102;BA.debugLine="If i2 > 0 Then";
if (_i2>0) { 
 //BA.debugLineNum = 103;BA.debugLine="columns.Append(\", \")";
_columns.Append(", ");
 //BA.debugLineNum = 104;BA.debugLine="values.Append(\", \")";
_values.Append(", ");
 };
 //BA.debugLineNum = 106;BA.debugLine="columns.Append(\"[\").Append(col).Append(\"]\")";
_columns.Append("[").Append(_col).Append("]");
 //BA.debugLineNum = 107;BA.debugLine="values.Append(\"?\")";
_values.Append("?");
 //BA.debugLineNum = 108;BA.debugLine="listOfValues.Add(value)";
_listofvalues.Add(_value);
 }
};
 //BA.debugLineNum = 110;BA.debugLine="sb.Append(columns.ToString).Append(\") VALUES (\"";
_sb.Append(_columns.ToString()).Append(") VALUES (").Append(_values.ToString()).Append(")");
 //BA.debugLineNum = 111;BA.debugLine="If i1 = 0 Then Log(\"InsertMaps (first query out";
if (_i1==0) { 
anywheresoftware.b4a.keywords.Common.Log("InsertMaps (first query out of "+BA.NumberToString(_listofmaps.getSize())+"): "+_sb.ToString());};
 //BA.debugLineNum = 112;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, listOfValues)";
_sql.ExecNonQuery2(_sb.ToString(),_listofvalues);
 }
};
 //BA.debugLineNum = 114;BA.debugLine="SQL.TransactionSuccessful";
_sql.TransactionSuccessful();
 } 
       catch (Exception e37) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e37); //BA.debugLineNum = 116;BA.debugLine="ToastMessageShow(LastException.Message, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)));
 };
 //BA.debugLineNum = 119;BA.debugLine="SQL.EndTransaction";
_sql.EndTransaction();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim DB_REAL, DB_INTEGER, DB_BLOB, DB_TEXT As Stri";
_db_real = "";
_db_integer = "";
_db_blob = "";
_db_text = "";
 //BA.debugLineNum = 14;BA.debugLine="DB_REAL = \"REAL\"";
_db_real = "REAL";
 //BA.debugLineNum = 15;BA.debugLine="DB_INTEGER = \"INTEGER\"";
_db_integer = "INTEGER";
 //BA.debugLineNum = 16;BA.debugLine="DB_BLOB = \"BLOB\"";
_db_blob = "BLOB";
 //BA.debugLineNum = 17;BA.debugLine="DB_TEXT = \"TEXT\"";
_db_text = "TEXT";
 //BA.debugLineNum = 18;BA.debugLine="Dim HtmlCSS As String";
_htmlcss = "";
 //BA.debugLineNum = 19;BA.debugLine="HtmlCSS = \"table {width: 100%;border: 1px solid #";
_htmlcss = "table {width: 100%;border: 1px solid #cef;text-align: left; }"+" th { font-weight: bold;	background-color: #acf;	border-bottom: 1px solid #cef; }"+"td,th {	padding: 4px 5px; }"+".odd {background-color: #def; } .odd td {border-bottom: 1px solid #cef; }"+"a { text-decoration:none; color: #000;}";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _setdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,int _version) throws Exception{
 //BA.debugLineNum = 391;BA.debugLine="Sub SetDBVersion (SQL As SQL, Version As Int)";
 //BA.debugLineNum = 392;BA.debugLine="SQL.ExecNonQuery2(\"UPDATE DBVersion set version =";
_sql.ExecNonQuery2("UPDATE DBVersion set version = ?",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_version)}));
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _updaterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _field,Object _newvalue,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 124;BA.debugLine="Sub UpdateRecord(SQL As SQL, TableName As String,";
 //BA.debugLineNum = 126;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 127;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 128;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"]";
_sb.Append("UPDATE [").Append(_tablename).Append("] SET [").Append(_field).Append("] = ? WHERE ");
 //BA.debugLineNum = 129;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 130;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 //BA.debugLineNum = 131;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 133;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 134;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 135;BA.debugLine="args.Add(NewValue)";
_args.Add(_newvalue);
 //BA.debugLineNum = 136;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step11 = 1;
final int limit11 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;(step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11) ;_i = ((int)(0 + _i + step11))  ) {
 //BA.debugLineNum = 137;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
if (_i>0) { 
_sb.Append(" AND ");};
 //BA.debugLineNum = 138;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 139;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 141;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.Log("UpdateRecord: "+_sb.ToString());
 //BA.debugLineNum = 142;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _updaterecord2(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fields,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 147;BA.debugLine="Sub UpdateRecord2(SQL As SQL, TableName As String,";
 //BA.debugLineNum = 148;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 149;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 //BA.debugLineNum = 150;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 152;BA.debugLine="If Fields.Size = 0 Then";
if (_fields.getSize()==0) { 
 //BA.debugLineNum = 153;BA.debugLine="Log(\"Fields empty\")";
anywheresoftware.b4a.keywords.Common.Log("Fields empty");
 //BA.debugLineNum = 154;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 156;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 157;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 158;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"]";
_sb.Append("UPDATE [").Append(_tablename).Append("] SET ");
 //BA.debugLineNum = 159;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 160;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 161;BA.debugLine="For i=0 To Fields.Size-1";
{
final int step14 = 1;
final int limit14 = (int) (_fields.getSize()-1);
_i = (int) (0) ;
for (;(step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14) ;_i = ((int)(0 + _i + step14))  ) {
 //BA.debugLineNum = 162;BA.debugLine="If i<>Fields.Size-1 Then";
if (_i!=_fields.getSize()-1) { 
 //BA.debugLineNum = 163;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Appen";
_sb.Append("[").Append(BA.ObjectToString(_fields.GetKeyAt(_i))).Append("]=?,");
 }else {
 //BA.debugLineNum = 165;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Appen";
_sb.Append("[").Append(BA.ObjectToString(_fields.GetKeyAt(_i))).Append("]=?");
 };
 //BA.debugLineNum = 167;BA.debugLine="args.Add(Fields.GetValueAt(i))";
_args.Add(_fields.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 170;BA.debugLine="sb.Append(\" WHERE \")";
_sb.Append(" WHERE ");
 //BA.debugLineNum = 171;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step23 = 1;
final int limit23 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;(step23 > 0 && _i <= limit23) || (step23 < 0 && _i >= limit23) ;_i = ((int)(0 + _i + step23))  ) {
 //BA.debugLineNum = 172;BA.debugLine="If i > 0 Then";
if (_i>0) { 
 //BA.debugLineNum = 173;BA.debugLine="sb.Append(\" AND \")";
_sb.Append(" AND ");
 };
 //BA.debugLineNum = 175;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 176;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 178;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.Log("UpdateRecord: "+_sb.ToString());
 //BA.debugLineNum = 179;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
}
