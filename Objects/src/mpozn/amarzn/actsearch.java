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

public class actsearch extends Activity implements B4AActivity{
	public static actsearch mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "mpozn.amarzn", "mpozn.amarzn.actsearch");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actsearch).");
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
		activityBA = new BA(this, layout, processBA, "mpozn.amarzn", "mpozn.amarzn.actsearch");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "mpozn.amarzn.actsearch", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actsearch) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actsearch) Resume **");
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
		return actsearch.class;
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
        BA.LogInfo("** Activity (actsearch) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (actsearch) Resume **");
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
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
public anywheresoftware.b4a.objects.PanelWrapper _mainpanel = null;
public mpozn.amarzn.searchview _sv = null;
public Object _index = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public mpozn.amarzn.main _main = null;
public mpozn.amarzn.starter _starter = null;
public mpozn.amarzn.dbutils _dbutils = null;
public mpozn.amarzn.actsal _actsal = null;
public mpozn.amarzn.actsalchild _actsalchild = null;
public mpozn.amarzn.actsalviewer _actsalviewer = null;
public mpozn.amarzn.actotherviewer _actotherviewer = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="dir=DBUtils.CopyDBFromAssets(\"amar96.db\")";
_dir = mostCurrent._dbutils._copydbfromassets(mostCurrent.activityBA,"amar96.db");
 //BA.debugLineNum = 56;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 57;BA.debugLine="SQL1.Initialize(dir, \"amar96.db\", False)";
_sql1.Initialize(_dir,"amar96.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 60;BA.debugLine="Activity.LoadLayout(\"laySearch\")";
mostCurrent._activity.LoadLayout("laySearch",mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="createSerch";
_createserch();
 //BA.debugLineNum = 64;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
mostCurrent._myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 65;BA.debugLine="SetTypeface(mainPanel,myFont)";
_settypeface(mostCurrent._mainpanel,mostCurrent._myfont);
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 70;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 71;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 73;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 85;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _createserch() throws Exception{
anywheresoftware.b4a.objects.collections.List _ind = null;
int _i = 0;
 //BA.debugLineNum = 27;BA.debugLine="Sub createSerch";
 //BA.debugLineNum = 28;BA.debugLine="sv.Initialize(Me, \"sv\")";
mostCurrent._sv._initialize(mostCurrent.activityBA,actsearch.getObject(),"sv");
 //BA.debugLineNum = 29;BA.debugLine="Dim ind As List";
_ind = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 30;BA.debugLine="ind.Initialize";
_ind.Initialize();
 //BA.debugLineNum = 31;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT cname FROM child";
mostCurrent._cursor1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT cname FROM child")));
 //BA.debugLineNum = 32;BA.debugLine="For i = 0 To Cursor1.RowCount - 1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._cursor1.getRowCount()-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 33;BA.debugLine="Cursor1.Position = i";
mostCurrent._cursor1.setPosition(_i);
 //BA.debugLineNum = 34;BA.debugLine="ind.Add(Cursor1.GetString(\"cname\"))";
_ind.Add((Object)(mostCurrent._cursor1.GetString("cname")));
 }
};
 //BA.debugLineNum = 36;BA.debugLine="Cursor1.Close";
mostCurrent._cursor1.Close();
 //BA.debugLineNum = 37;BA.debugLine="index = sv.SetItems(ind)";
mostCurrent._index = mostCurrent._sv._setitems(_ind);
 //BA.debugLineNum = 38;BA.debugLine="sv.SetIndex(index)";
mostCurrent._sv._setindex(mostCurrent._index);
 //BA.debugLineNum = 39;BA.debugLine="sv.AddToParent(mainPanel, 1%x, 11%y, 98%x, 88%y)";
mostCurrent._sv._addtoparent(mostCurrent._mainpanel,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (11),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (98),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (88),mostCurrent.activityBA));
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim myFont As Typeface";
mostCurrent._myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private mainPanel As Panel";
mostCurrent._mainpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim sv As SearchView";
mostCurrent._sv = new mpozn.amarzn.searchview();
 //BA.debugLineNum = 16;BA.debugLine="Dim index As Object";
mostCurrent._index = new Object();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim SQL1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 8;BA.debugLine="Dim dir As String";
_dir = "";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _settypeface(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 42;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 43;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _parent;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 44;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 45;BA.debugLine="SetTypeface(v, t)";
_settypeface((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 47;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 48;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
};
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(String _value) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub sv_ItemClick(Value As String)";
 //BA.debugLineNum = 77;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT cid FROM child w";
mostCurrent._cursor1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT cid FROM child where cname = '"+_value+"'")));
 //BA.debugLineNum = 78;BA.debugLine="Cursor1.Position = 0";
mostCurrent._cursor1.setPosition((int) (0));
 //BA.debugLineNum = 79;BA.debugLine="actSalViewer.cid = Cursor1.GetString(\"cid\")";
mostCurrent._actsalviewer._cid = mostCurrent._cursor1.GetString("cid");
 //BA.debugLineNum = 80;BA.debugLine="StartActivity(actSalViewer)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsalviewer.getObject()));
 //BA.debugLineNum = 81;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
}
