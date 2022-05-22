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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "mpozn.amarzn", "mpozn.amarzn.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "mpozn.amarzn", "mpozn.amarzn.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "mpozn.amarzn.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static String _pagetitle = "";
public static String _pageurl = "";
public static anywheresoftware.b4a.objects.Timer _welcometimer = null;
public anywheresoftware.b4a.objects.PanelWrapper _welcomepanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _homepanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnabout = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnsal = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnhamkar = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnmanabe = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnpish = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _headerimg = null;
public anywheresoftware.b4a.samples.httputils2.httpjob _jobupdateapk = null;
public anywheresoftware.b4a.samples.httputils2.httpjob _jobautowebversion = null;
public anywheresoftware.b4a.objects.LabelWrapper _versionlabel = null;
public anywheresoftware.b4a.objects.AnimationWrapper _sal_anim = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _headerimg_sal = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public mpozn.amarzn.starter _starter = null;
public mpozn.amarzn.dbutils _dbutils = null;
public mpozn.amarzn.actsal _actsal = null;
public mpozn.amarzn.actsalchild _actsalchild = null;
public mpozn.amarzn.actsalviewer _actsalviewer = null;
public mpozn.amarzn.actsearch _actsearch = null;
public mpozn.amarzn.actotherviewer _actotherviewer = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (actsal.mostCurrent != null);
vis = vis | (actsalchild.mostCurrent != null);
vis = vis | (actsalviewer.mostCurrent != null);
vis = vis | (actsearch.mostCurrent != null);
vis = vis | (actotherviewer.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 61;BA.debugLine="Activity.LoadLayout(\"layhome\")";
mostCurrent._activity.LoadLayout("layhome",mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="Dim myFont As Typeface";
_myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 64;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
_myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 65;BA.debugLine="SetTypeface(homePanel,myFont)";
_settypeface(mostCurrent._homepanel,_myfont);
 //BA.debugLineNum = 67;BA.debugLine="versionLabel.Text = \"Version: \" & Application.Ver";
mostCurrent._versionlabel.setText(BA.ObjectToCharSequence("Version: "+anywheresoftware.b4a.keywords.Common.Application.getVersionName()));
 //BA.debugLineNum = 69;BA.debugLine="showWelcome(3000)";
_showwelcome((int) (3000));
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 119;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 120;BA.debugLine="If Msgbox2(\"آیا میخواهید از برنامه خارج شوید؟\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("آیا میخواهید از برنامه خارج شوید؟"),BA.ObjectToCharSequence("توجه"),"بلی","","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 121;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 124;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
 //BA.debugLineNum = 150;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 151;BA.debugLine="If Connected Then";
if (_connected()) { 
 //BA.debugLineNum = 152;BA.debugLine="Dim pi As PhoneIntents";
_pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 153;BA.debugLine="StartActivity(pi.OpenBrowser(\"http://www.mpo-zn.";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pi.OpenBrowser("http://www.mpo-zn.ir")));
 }else {
 //BA.debugLineNum = 155;BA.debugLine="ToastMessageShow(\"ارتباط با اینترنت برقرار نیست.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ارتباط با اینترنت برقرار نیست."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _btnhamkar_click() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub btnHamkar_Click";
 //BA.debugLineNum = 139;BA.debugLine="pageTitle = \"اسامی همکاران\"";
_pagetitle = "اسامی همکاران";
 //BA.debugLineNum = 140;BA.debugLine="pageUrl = \"file:///android_asset/data/hamkar.htm\"";
_pageurl = "file:///android_asset/data/hamkar.htm";
 //BA.debugLineNum = 141;BA.debugLine="StartActivity(actOtherViewer)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actotherviewer.getObject()));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _btnmanabe_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub btnManabe_Click";
 //BA.debugLineNum = 145;BA.debugLine="pageTitle = \"منابع و مآخذ\"";
_pagetitle = "منابع و مآخذ";
 //BA.debugLineNum = 146;BA.debugLine="pageUrl = \"file:///android_asset/data/manabe.htm\"";
_pageurl = "file:///android_asset/data/manabe.htm";
 //BA.debugLineNum = 147;BA.debugLine="StartActivity(actOtherViewer)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actotherviewer.getObject()));
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _btnpish_click() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub btnPish_Click";
 //BA.debugLineNum = 133;BA.debugLine="pageTitle = \"پیشگفتار\"";
_pagetitle = "پیشگفتار";
 //BA.debugLineNum = 134;BA.debugLine="pageUrl = \"file:///android_asset/data/pish.htm\"";
_pageurl = "file:///android_asset/data/pish.htm";
 //BA.debugLineNum = 135;BA.debugLine="StartActivity(actOtherViewer)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actotherviewer.getObject()));
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _btnsal_click() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub btnSal_Click";
 //BA.debugLineNum = 128;BA.debugLine="pageTitle = \"سالنامه آماری زنجان\"";
_pagetitle = "سالنامه آماری زنجان";
 //BA.debugLineNum = 129;BA.debugLine="StartActivity(actSal)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsal.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static boolean  _connected() throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _response = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _error = null;
 //BA.debugLineNum = 47;BA.debugLine="Sub Connected As Boolean";
 //BA.debugLineNum = 48;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 49;BA.debugLine="Dim Response, Error As StringBuilder";
_response = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_error = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Response.Initialize";
_response.Initialize();
 //BA.debugLineNum = 51;BA.debugLine="Error.Initialize";
_error.Initialize();
 //BA.debugLineNum = 52;BA.debugLine="p.Shell(\"ping -c 1 8.8.8.8\",Null,Response,Error)";
_p.Shell("ping -c 1 8.8.8.8",(String[])(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(_response.getObject()),(java.lang.StringBuilder)(_error.getObject()));
 //BA.debugLineNum = 53;BA.debugLine="If Error.ToString=\"\" Then";
if ((_error.ToString()).equals("")) { 
 //BA.debugLineNum = 54;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 56;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return false;
}
public static String  _enableall(anywheresoftware.b4a.objects.PanelWrapper _p,boolean _enabled) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 108;BA.debugLine="Sub EnableAll(p As Panel, Enabled As Boolean)";
 //BA.debugLineNum = 109;BA.debugLine="For Each v As View In p";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _p;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 110;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 111;BA.debugLine="EnableAll(v, Enabled)";
_enableall((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_enabled);
 }else {
 //BA.debugLineNum = 113;BA.debugLine="v.Enabled = Enabled";
_v.setEnabled(_enabled);
 };
 }
};
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Dim welcomePanel As Panel";
mostCurrent._welcomepanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim homePanel As Panel";
mostCurrent._homepanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnAbout As Panel";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnSal As Panel";
mostCurrent._btnsal = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnHamkar As Panel";
mostCurrent._btnhamkar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnManabe As Panel";
mostCurrent._btnmanabe = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnPish As Panel";
mostCurrent._btnpish = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private headerImg As ImageView";
mostCurrent._headerimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim jobUpdateAPK, jobAutoWebVersion As HttpJob";
mostCurrent._jobupdateapk = new anywheresoftware.b4a.samples.httputils2.httpjob();
mostCurrent._jobautowebversion = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 31;BA.debugLine="Private versionLabel As Label";
mostCurrent._versionlabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim sal_anim As Animation";
mostCurrent._sal_anim = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private headerImg_sal As ImageView";
mostCurrent._headerimg_sal = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _webversion = "";
byte[] _buffer = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 159;BA.debugLine="Sub JobDone(job As HttpJob)";
 //BA.debugLineNum = 160;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 161;BA.debugLine="Select job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"jobAutoWebVersion","jobUpdateAPK")) {
case 0: {
 //BA.debugLineNum = 164;BA.debugLine="Dim webVersion As String";
_webversion = "";
 //BA.debugLineNum = 165;BA.debugLine="Dim Buffer(3) As Byte";
_buffer = new byte[(int) (3)];
;
 //BA.debugLineNum = 166;BA.debugLine="job.GetInputStream.ReadBytes(Buffer,0,Buffer.L";
_job._getinputstream().ReadBytes(_buffer,(int) (0),_buffer.length);
 //BA.debugLineNum = 167;BA.debugLine="webVersion = BytesToString(Buffer,0,Buffer.Len";
_webversion = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 168;BA.debugLine="If Application.VersionName < webVersion Then";
if ((double)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.Application.getVersionName()))<(double)(Double.parseDouble(_webversion))) { 
 //BA.debugLineNum = 169;BA.debugLine="If Msgbox2(\"نسخه \"&webVersion&\" موجود است. آی";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("نسخه "+_webversion+" موجود است. آیا میخواهید به روز رسانی انجام شود؟"),BA.ObjectToCharSequence("به روز رسانی"),"بلی","","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 170;BA.debugLine="ProgressDialogShow(\"در حال به روز رسانی نرم";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("در حال به روز رسانی نرم افزار"+anywheresoftware.b4a.keywords.Common.CRLF+"لطفا منتظر بمانید"));
 //BA.debugLineNum = 171;BA.debugLine="jobUpdateAPK.Initialize(\"jobUpdateAPK\", Me)";
mostCurrent._jobupdateapk._initialize(processBA,"jobUpdateAPK",main.getObject());
 //BA.debugLineNum = 172;BA.debugLine="jobUpdateAPK.Download(\"http://www.limopack.c";
mostCurrent._jobupdateapk._download("http://www.limopack.com/appup/Amar_zn.apk");
 };
 };
 break; }
case 1: {
 //BA.debugLineNum = 177;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 178;BA.debugLine="out = File.OpenOutput(File.DirDefaultExternal,";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Amar_zn.apk",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 179;BA.debugLine="File.Copy2(job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 180;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 181;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 183;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 184;BA.debugLine="i.Initialize(i.ACTION_VIEW, \"file://\" & File.C";
_i.Initialize(_i.ACTION_VIEW,"file://"+anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Amar_zn.apk"));
 //BA.debugLineNum = 185;BA.debugLine="i.SetType(\"application/vnd.android.package-arc";
_i.SetType("application/vnd.android.package-archive");
 //BA.debugLineNum = 186;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 break; }
}
;
 }else {
 //BA.debugLineNum = 189;BA.debugLine="ToastMessageShow(\"Error: \" & job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 191;BA.debugLine="job.Release";
_job._release();
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
main._process_globals();
starter._process_globals();
dbutils._process_globals();
actsal._process_globals();
actsalchild._process_globals();
actsalviewer._process_globals();
actsearch._process_globals();
actotherviewer._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim pageTitle As String";
_pagetitle = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim pageUrl As String";
_pageurl = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim welcomeTimer As Timer";
_welcometimer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _settypeface(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 36;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 37;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _parent;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v.setObject((android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 38;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 39;BA.debugLine="SetTypeface(v, t)";
_settypeface((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 41;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 42;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
};
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _showwelcome(int _duration) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub showWelcome(duration As Int)";
 //BA.debugLineNum = 74;BA.debugLine="EnableAll(homePanel,False)";
_enableall(mostCurrent._homepanel,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 75;BA.debugLine="welcomeTimer.Initialize(\"welTimer\",duration)";
_welcometimer.Initialize(processBA,"welTimer",(long) (_duration));
 //BA.debugLineNum = 76;BA.debugLine="welcomeTimer.Enabled = True";
_welcometimer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _weltimer_tick() throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub welTimer_tick";
 //BA.debugLineNum = 80;BA.debugLine="welcomeTimer.Enabled = False";
_welcometimer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 81;BA.debugLine="welcomePanel.Visible = False";
mostCurrent._welcomepanel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 82;BA.debugLine="homePanel.Visible = True";
mostCurrent._homepanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="EnableAll(homePanel,True)";
_enableall(mostCurrent._homepanel,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 85;BA.debugLine="If Connected Then";
if (_connected()) { 
 //BA.debugLineNum = 86;BA.debugLine="jobAutoWebVersion.Initialize(\"jobAutoWebVersion\"";
mostCurrent._jobautowebversion._initialize(processBA,"jobAutoWebVersion",main.getObject());
 //BA.debugLineNum = 87;BA.debugLine="jobAutoWebVersion.Download(\"http://www.limopack.";
mostCurrent._jobautowebversion._download("http://www.limopack.com/appup/amar.inf");
 }else {
 //BA.debugLineNum = 89;BA.debugLine="ToastMessageShow(\"ارتباط با اینترنت برقرار نیست.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("ارتباط با اینترنت برقرار نیست."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 92;BA.debugLine="sal_anim.InitializeScaleCenter(\"sal_anim\",1,1,1.0";
mostCurrent._sal_anim.InitializeScaleCenter(mostCurrent.activityBA,"sal_anim",(float) (1),(float) (1),(float) (1.05),(float) (1.05),(android.view.View)(mostCurrent._headerimg_sal.getObject()));
 //BA.debugLineNum = 93;BA.debugLine="sal_anim.RepeatMode = sal_anim.REPEAT_REVERSE";
mostCurrent._sal_anim.setRepeatMode(mostCurrent._sal_anim.REPEAT_REVERSE);
 //BA.debugLineNum = 94;BA.debugLine="sal_anim.Duration = 1000";
mostCurrent._sal_anim.setDuration((long) (1000));
 //BA.debugLineNum = 95;BA.debugLine="sal_anim.RepeatCount = 10";
mostCurrent._sal_anim.setRepeatCount((int) (10));
 //BA.debugLineNum = 96;BA.debugLine="sal_anim.Start(headerImg_sal)";
mostCurrent._sal_anim.Start((android.view.View)(mostCurrent._headerimg_sal.getObject()));
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
}
