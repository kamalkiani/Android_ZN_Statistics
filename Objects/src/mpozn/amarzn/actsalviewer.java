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

public class actsalviewer extends Activity implements B4AActivity{
	public static actsalviewer mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "mpozn.amarzn", "mpozn.amarzn.actsalviewer");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actsalviewer).");
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
		activityBA = new BA(this, layout, processBA, "mpozn.amarzn", "mpozn.amarzn.actsalviewer");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "mpozn.amarzn.actsalviewer", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actsalviewer) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actsalviewer) Resume **");
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
		return actsalviewer.class;
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
        BA.LogInfo("** Activity (actsalviewer) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (actsalviewer) Resume **");
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
public static String _cid = "";
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
public anywheresoftware.b4a.objects.PanelWrapper _mainpanel = null;
public anywheresoftware.b4a.objects.LabelWrapper _titlelabel = null;
public static String _filepath = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btnfarabar = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public mpozn.amarzn.main _main = null;
public mpozn.amarzn.starter _starter = null;
public mpozn.amarzn.dbutils _dbutils = null;
public mpozn.amarzn.actsal _actsal = null;
public mpozn.amarzn.actsalchild _actsalchild = null;
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
String _myhtml = "";
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="dir=DBUtils.CopyDBFromAssets(\"amar96.db\")";
_dir = mostCurrent._dbutils._copydbfromassets(mostCurrent.activityBA,"amar96.db");
 //BA.debugLineNum = 44;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 45;BA.debugLine="SQL1.Initialize(dir, \"amar96.db\", False)";
_sql1.Initialize(_dir,"amar96.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 47;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT cname, filename,";
mostCurrent._cursor1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT cname, filename, url FROM child where cid ='"+_cid+"'")));
 //BA.debugLineNum = 49;BA.debugLine="Activity.LoadLayout(\"laySalviewer\")";
mostCurrent._activity.LoadLayout("laySalviewer",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
mostCurrent._myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 51;BA.debugLine="SetTypeface(mainPanel,myFont)";
_settypeface(mostCurrent._mainpanel,mostCurrent._myfont);
 //BA.debugLineNum = 53;BA.debugLine="Cursor1.Position = 0";
mostCurrent._cursor1.setPosition((int) (0));
 //BA.debugLineNum = 54;BA.debugLine="titleLabel.Text = Cursor1.GetString(\"cname\")";
mostCurrent._titlelabel.setText(BA.ObjectToCharSequence(mostCurrent._cursor1.GetString("cname")));
 //BA.debugLineNum = 56;BA.debugLine="If Cursor1.GetString(\"filename\").Contains(\"n\") Th";
if (mostCurrent._cursor1.GetString("filename").contains("n")) { 
 //BA.debugLineNum = 57;BA.debugLine="Dim myhtml As String = \"<!DOCTYPE html><html><he";
_myhtml = "<!DOCTYPE html><html><head><style>.myImg{width:90%;display: block;margin-left: auto;margin-right: auto;padding:15px;}</style></head> <body> <img src=\"file:///android_asset/data/img/"+mostCurrent._cursor1.GetString("filename")+".jpg\" class=\"myImg\"></body></html>";
 //BA.debugLineNum = 58;BA.debugLine="WebView1.LoadHtml(myhtml)";
mostCurrent._webview1.LoadHtml(_myhtml);
 }else {
 //BA.debugLineNum = 60;BA.debugLine="filepath = \"file:///android_asset/data/\" & Curso";
mostCurrent._filepath = "file:///android_asset/data/"+mostCurrent._cursor1.GetString("filename")+".htm";
 //BA.debugLineNum = 61;BA.debugLine="WebView1.LoadUrl(filepath)";
mostCurrent._webview1.LoadUrl(mostCurrent._filepath);
 };
 //BA.debugLineNum = 64;BA.debugLine="If Cursor1.GetString(\"url\") <> Null Then";
if (mostCurrent._cursor1.GetString("url")!= null) { 
 //BA.debugLineNum = 65;BA.debugLine="btnFarabar.Enabled = True";
mostCurrent._btnfarabar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 68;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 69;BA.debugLine="p.SetScreenOrientation(-1)";
_p.SetScreenOrientation(processBA,(int) (-1));
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 74;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 75;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 77;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 108;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _btnfarabar_click() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub btnFarabar_Click";
 //BA.debugLineNum = 81;BA.debugLine="If Connected Then";
if (_connected()) { 
 //BA.debugLineNum = 82;BA.debugLine="ToastMessageShow(\"در حال دریافت اطلاعات\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("در حال دریافت اطلاعات"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="WebView1.LoadUrl(Cursor1.GetString(\"url\"))";
mostCurrent._webview1.LoadUrl(mostCurrent._cursor1.GetString("url"));
 }else {
 //BA.debugLineNum = 85;BA.debugLine="ToastMessageShow(\"ارتباط با سرور برقرار نیست. لط";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ارتباط با سرور برقرار نیست. لطفا اتصال اینترنت دستگاه را کنترل کنید"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _btnsearch_click() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub btnSearch_Click";
 //BA.debugLineNum = 103;BA.debugLine="StartActivity(actSearch)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsearch.getObject()));
 //BA.debugLineNum = 104;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static boolean  _connected() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _response = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _error = null;
 //BA.debugLineNum = 89;BA.debugLine="Sub Connected As Boolean";
 //BA.debugLineNum = 90;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 91;BA.debugLine="Dim Response, Error As StringBuilder";
_response = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_error = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Response.Initialize";
_response.Initialize();
 //BA.debugLineNum = 93;BA.debugLine="Error.Initialize";
_error.Initialize();
 //BA.debugLineNum = 94;BA.debugLine="p.Shell(\"ping -c 1 8.8.8.8\",Null,Response,Error)";
_p.Shell("ping -c 1 8.8.8.8",(String[])(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(_response.getObject()),(java.lang.StringBuilder)(_error.getObject()));
 //BA.debugLineNum = 95;BA.debugLine="If Error.ToString=\"\" Then";
if ((_error.ToString()).equals("")) { 
 //BA.debugLineNum = 96;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 98;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return false;
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
 //BA.debugLineNum = 17;BA.debugLine="Dim filepath As String";
mostCurrent._filepath = "";
 //BA.debugLineNum = 18;BA.debugLine="Private btnFarabar As Button";
mostCurrent._btnfarabar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim SQL1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 8;BA.debugLine="Dim dir As String";
_dir = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim cid As String";
_cid = "";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _settypeface(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 30;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 31;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _parent;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 32;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 33;BA.debugLine="SetTypeface(v, t)";
_settypeface((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 35;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 36;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
};
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
}
