package mpozn.amarzn;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class actsalchild extends Activity implements B4AActivity{
	public static actsalchild mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "mpozn.amarzn", "mpozn.amarzn.actsalchild");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actsalchild).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "mpozn.amarzn", "mpozn.amarzn.actsalchild");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "mpozn.amarzn.actsalchild", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actsalchild) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actsalchild) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return actsalchild.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (actsalchild) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (actsalchild) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _sql1 = null;
public static String _dir = "";
public static String _pid = "";
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
public anywheresoftware.b4a.objects.PanelWrapper _mainpanel = null;
public anywheresoftware.b4a.objects.LabelWrapper _titlelabel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _grohlistview = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public mpozn.amarzn.main _main = null;
public mpozn.amarzn.starter _starter = null;
public mpozn.amarzn.dbutils _dbutils = null;
public mpozn.amarzn.actsal _actsal = null;
public mpozn.amarzn.actsalviewer _actsalviewer = null;
public mpozn.amarzn.actsearch _actsearch = null;
public mpozn.amarzn.actotherviewer _actotherviewer = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="dir=DBUtils.CopyDBFromAssets(\"amar96.db\")";
_dir = mostCurrent._dbutils._copydbfromassets(mostCurrent.activityBA,"amar96.db");
 //BA.debugLineNum = 42;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 43;BA.debugLine="SQL1.Initialize(dir, \"amar96.db\", False)";
_sql1.Initialize(_dir,"amar96.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 46;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT pname FROM paren";
mostCurrent._cursor1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT pname FROM parent where pid ='"+_pid+"'")));
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"laySalchild\")";
mostCurrent._activity.LoadLayout("laySalchild",mostCurrent.activityBA);
 //BA.debugLineNum = 49;BA.debugLine="Cursor1.Position = 0";
mostCurrent._cursor1.setPosition((int) (0));
 //BA.debugLineNum = 50;BA.debugLine="titleLabel.Text = Cursor1.GetString(\"pname\")";
mostCurrent._titlelabel.setText(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("pname")));
 //BA.debugLineNum = 52;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
mostCurrent._myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 53;BA.debugLine="SetTypeface(mainPanel,myFont)";
_settypeface(mostCurrent._mainpanel,mostCurrent._myfont);
 //BA.debugLineNum = 55;BA.debugLine="BuildItems_groh";
_builditems_groh();
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 59;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 60;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 62;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 110;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _btnsearch_click() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub btnSearch_Click";
 //BA.debugLineNum = 106;BA.debugLine="StartActivity(actSearch)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsearch.getObject()));
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _builditems_groh() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _gd = null;
int _i = 0;
 //BA.debugLineNum = 65;BA.debugLine="Sub BuildItems_groh";
 //BA.debugLineNum = 66;BA.debugLine="grohListView.Clear";
mostCurrent._grohlistview.Clear();
 //BA.debugLineNum = 67;BA.debugLine="grohListView.TwoLinesAndBitmap.ItemHeight = 12%y";
mostCurrent._grohlistview.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 68;BA.debugLine="grohListView.TwoLinesAndBitmap.Label.TextColor =";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 69;BA.debugLine="grohListView.TwoLinesAndBitmap.SecondLabel.TextCo";
mostCurrent._grohlistview.getTwoLinesAndBitmap().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 70;BA.debugLine="grohListView.TwoLinesAndBitmap.Label.Gravity = Gr";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 71;BA.debugLine="grohListView.TwoLinesAndBitmap.SecondLabel.Gravit";
mostCurrent._grohlistview.getTwoLinesAndBitmap().SecondLabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 72;BA.debugLine="grohListView.TwoLinesAndBitmap.Label.TextSize = 1";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Label.setTextSize((float) (16));
 //BA.debugLineNum = 73;BA.debugLine="grohListView.TwoLinesAndBitmap.SecondLabel.TextSi";
mostCurrent._grohlistview.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (15));
 //BA.debugLineNum = 74;BA.debugLine="grohListView.TwoLinesAndBitmap.Label.Typeface = m";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Label.setTypeface((android.graphics.Typeface)(mostCurrent._myfont.getObject()));
 //BA.debugLineNum = 75;BA.debugLine="grohListView.TwoLinesAndBitmap.SecondLabel.Typefa";
mostCurrent._grohlistview.getTwoLinesAndBitmap().SecondLabel.setTypeface((android.graphics.Typeface)(mostCurrent._myfont.getObject()));
 //BA.debugLineNum = 76;BA.debugLine="grohListView.TwoLinesAndBitmap.Label.SetLayout(15";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Label.SetLayout((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (4),mostCurrent.activityBA));
 //BA.debugLineNum = 77;BA.debugLine="grohListView.TwoLinesAndBitmap.SecondLabel.SetLay";
mostCurrent._grohlistview.getTwoLinesAndBitmap().SecondLabel.SetLayout((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA));
 //BA.debugLineNum = 78;BA.debugLine="grohListView.TwoLinesAndBitmap.ImageView.SetLayou";
mostCurrent._grohlistview.getTwoLinesAndBitmap().ImageView.SetLayout(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (17),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (17),mostCurrent.activityBA));
 //BA.debugLineNum = 79;BA.debugLine="grohListView.Color = Colors.Transparent";
mostCurrent._grohlistview.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 80;BA.debugLine="Dim gd As ColorDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 81;BA.debugLine="gd.Initialize(Colors.RGB(240,240,240),0)";
_gd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (240),(int) (240),(int) (240)),(int) (0));
 //BA.debugLineNum = 82;BA.debugLine="grohListView.TwoLinesAndBitmap.Background = gd";
mostCurrent._grohlistview.getTwoLinesAndBitmap().Background = (android.graphics.drawable.Drawable)(_gd.getObject());
 //BA.debugLineNum = 84;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT * FROM child whe";
mostCurrent._cursor1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM child where pid='"+_pid+"'")));
 //BA.debugLineNum = 85;BA.debugLine="For i = 0 To Cursor1.RowCount-1";
{
final int step19 = 1;
final int limit19 = (int) (mostCurrent._cursor1.getRowCount()-1);
_i = (int) (0) ;
for (;(step19 > 0 && _i <= limit19) || (step19 < 0 && _i >= limit19) ;_i = ((int)(0 + _i + step19))  ) {
 //BA.debugLineNum = 86;BA.debugLine="Cursor1.Position = i";
mostCurrent._cursor1.setPosition(_i);
 //BA.debugLineNum = 87;BA.debugLine="If Cursor1.GetString(\"ctype\").Contains(\"جدول\")";
if (mostCurrent._cursor1.GetString("ctype").contains("جدول")) { 
 //BA.debugLineNum = 88;BA.debugLine="grohListView.AddTwoLinesAndBitmap2(Cursor1.GetS";
mostCurrent._grohlistview.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("ctype")),BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("cname")),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"table.png").getObject()),(Object)(mostCurrent._cursor1.GetString("cid")));
 }else if(mostCurrent._cursor1.GetString("ctype").contains("نمودار")) { 
 //BA.debugLineNum = 90;BA.debugLine="grohListView.AddTwoLinesAndBitmap2(Cursor1.GetS";
mostCurrent._grohlistview.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("ctype")),BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("cname")),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"chart.png").getObject()),(Object)(mostCurrent._cursor1.GetString("cid")));
 }else if(mostCurrent._cursor1.GetString("ctype").contains("نقشه")) { 
 //BA.debugLineNum = 92;BA.debugLine="grohListView.AddTwoLinesAndBitmap2(Cursor1.GetS";
mostCurrent._grohlistview.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("ctype")),BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("cname")),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"map.png").getObject()),(Object)(mostCurrent._cursor1.GetString("cid")));
 }else {
 //BA.debugLineNum = 94;BA.debugLine="grohListView.AddTwoLinesAndBitmap2(Cursor1.GetS";
mostCurrent._grohlistview.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("ctype")),BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("cname")),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro.png").getObject()),(Object)(mostCurrent._cursor1.GetString("cid")));
 };
 }
};
 //BA.debugLineNum = 97;BA.debugLine="Cursor1.Close";
mostCurrent._cursor1.Close();
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim myFont As Typeface";
mostCurrent._myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private mainPanel As Panel";
mostCurrent._mainpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private titleLabel As Label";
mostCurrent._titlelabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private grohListView As ListView";
mostCurrent._grohlistview = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _grohlistview_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub grohListView_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 101;BA.debugLine="actSalViewer.cid = Value";
mostCurrent._actsalviewer._cid = BA.ObjectToString(_value);
 //BA.debugLineNum = 102;BA.debugLine="StartActivity(actSalViewer)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsalviewer.getObject()));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim SQL1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 8;BA.debugLine="Dim dir As String";
_dir = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim pid As String";
_pid = "";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _settypeface(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 28;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 29;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _parent;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 30;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 31;BA.debugLine="SetTypeface(v, t)";
_settypeface((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 33;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 34;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
};
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
}
