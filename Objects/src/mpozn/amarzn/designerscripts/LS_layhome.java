package mpozn.amarzn.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layhome{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 5;BA.debugLine="welcomePanel.Width = 100%x"[layhome/General script]
views.get("welcomepanel").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 6;BA.debugLine="welcomePanel.Height = 100%y"[layhome/General script]
views.get("welcomepanel").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 7;BA.debugLine="welcomePanel.Left = 0"[layhome/General script]
views.get("welcomepanel").vw.setLeft((int)(0d));
//BA.debugLineNum = 8;BA.debugLine="welcomePanel.Top = 0"[layhome/General script]
views.get("welcomepanel").vw.setTop((int)(0d));
//BA.debugLineNum = 10;BA.debugLine="welcomeImg.Width = 100%x"[layhome/General script]
views.get("welcomeimg").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 11;BA.debugLine="welcomeImg.Height = 100%y"[layhome/General script]
views.get("welcomeimg").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 12;BA.debugLine="welcomeImg.Left = 0%y"[layhome/General script]
views.get("welcomeimg").vw.setLeft((int)((0d / 100 * height)));
//BA.debugLineNum = 13;BA.debugLine="welcomeImg.Top = 0%y"[layhome/General script]
views.get("welcomeimg").vw.setTop((int)((0d / 100 * height)));
//BA.debugLineNum = 15;BA.debugLine="homePanel.Width = 100%x"[layhome/General script]
views.get("homepanel").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 16;BA.debugLine="homePanel.Height = 100%y"[layhome/General script]
views.get("homepanel").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 17;BA.debugLine="homePanel.Left = 0"[layhome/General script]
views.get("homepanel").vw.setLeft((int)(0d));
//BA.debugLineNum = 18;BA.debugLine="homePanel.Top = 0"[layhome/General script]
views.get("homepanel").vw.setTop((int)(0d));
//BA.debugLineNum = 20;BA.debugLine="headerImg.Width = 100%x"[layhome/General script]
views.get("headerimg").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 21;BA.debugLine="headerImg.Height = 100%y"[layhome/General script]
views.get("headerimg").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 22;BA.debugLine="headerImg.Left = 0"[layhome/General script]
views.get("headerimg").vw.setLeft((int)(0d));
//BA.debugLineNum = 23;BA.debugLine="headerImg.Top = 0"[layhome/General script]
views.get("headerimg").vw.setTop((int)(0d));
//BA.debugLineNum = 25;BA.debugLine="headerImg_sal.Width = 100%x"[layhome/General script]
views.get("headerimg_sal").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 26;BA.debugLine="headerImg_sal.Height = 100%y"[layhome/General script]
views.get("headerimg_sal").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 27;BA.debugLine="headerImg_sal.Left = 0"[layhome/General script]
views.get("headerimg_sal").vw.setLeft((int)(0d));
//BA.debugLineNum = 28;BA.debugLine="headerImg_sal.Top = 0"[layhome/General script]
views.get("headerimg_sal").vw.setTop((int)(0d));
//BA.debugLineNum = 30;BA.debugLine="btnAbout.Width = 30%x"[layhome/General script]
views.get("btnabout").vw.setWidth((int)((30d / 100 * width)));
//BA.debugLineNum = 31;BA.debugLine="btnAbout.Height = 20%y"[layhome/General script]
views.get("btnabout").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 32;BA.debugLine="btnAbout.Left = 5%x"[layhome/General script]
views.get("btnabout").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 33;BA.debugLine="btnAbout.Top = 5%y"[layhome/General script]
views.get("btnabout").vw.setTop((int)((5d / 100 * height)));
//BA.debugLineNum = 35;BA.debugLine="btnSal.Width = 70%x"[layhome/General script]
views.get("btnsal").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 36;BA.debugLine="btnSal.Height = 25%y"[layhome/General script]
views.get("btnsal").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 37;BA.debugLine="btnSal.Left = 25%x"[layhome/General script]
views.get("btnsal").vw.setLeft((int)((25d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="btnSal.Top = 25%y"[layhome/General script]
views.get("btnsal").vw.setTop((int)((25d / 100 * height)));
//BA.debugLineNum = 40;BA.debugLine="btnPish.Width = 30%x"[layhome/General script]
views.get("btnpish").vw.setWidth((int)((30d / 100 * width)));
//BA.debugLineNum = 41;BA.debugLine="btnPish.Height = 20%y"[layhome/General script]
views.get("btnpish").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 42;BA.debugLine="btnPish.Left = 65%x"[layhome/General script]
views.get("btnpish").vw.setLeft((int)((65d / 100 * width)));
//BA.debugLineNum = 43;BA.debugLine="btnPish.Top = 50%y"[layhome/General script]
views.get("btnpish").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 45;BA.debugLine="btnHamkar.Width = 30%x"[layhome/General script]
views.get("btnhamkar").vw.setWidth((int)((30d / 100 * width)));
//BA.debugLineNum = 46;BA.debugLine="btnHamkar.Height = 20%y"[layhome/General script]
views.get("btnhamkar").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 47;BA.debugLine="btnHamkar.Left = 5%x"[layhome/General script]
views.get("btnhamkar").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 48;BA.debugLine="btnHamkar.Top = 50%y"[layhome/General script]
views.get("btnhamkar").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 50;BA.debugLine="btnManabe.Width = 30%x"[layhome/General script]
views.get("btnmanabe").vw.setWidth((int)((30d / 100 * width)));
//BA.debugLineNum = 51;BA.debugLine="btnManabe.Height = 20%y"[layhome/General script]
views.get("btnmanabe").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 52;BA.debugLine="btnManabe.Left = 35%x"[layhome/General script]
views.get("btnmanabe").vw.setLeft((int)((35d / 100 * width)));
//BA.debugLineNum = 53;BA.debugLine="btnManabe.Top = 50%y"[layhome/General script]
views.get("btnmanabe").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 55;BA.debugLine="versionLabel.Width = 80%x"[layhome/General script]
views.get("versionlabel").vw.setWidth((int)((80d / 100 * width)));
//BA.debugLineNum = 56;BA.debugLine="versionLabel.Height = 10%y"[layhome/General script]
views.get("versionlabel").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 57;BA.debugLine="versionLabel.Left = 10%x"[layhome/General script]
views.get("versionlabel").vw.setLeft((int)((10d / 100 * width)));
//BA.debugLineNum = 58;BA.debugLine="versionLabel.Top = 89%y"[layhome/General script]
views.get("versionlabel").vw.setTop((int)((89d / 100 * height)));

}
}