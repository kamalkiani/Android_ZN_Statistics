package mpozn.amarzn.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laysalviewer{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("mainpanel").vw.setLeft((int)(0d));
views.get("mainpanel").vw.setTop((int)(0d));
views.get("mainpanel").vw.setWidth((int)((100d / 100 * width)));
views.get("mainpanel").vw.setHeight((int)((100d / 100 * height)));
views.get("webview1").vw.setLeft((int)(0d));
views.get("webview1").vw.setTop((int)((90d * scale)));
views.get("webview1").vw.setWidth((int)((100d / 100 * width)));
views.get("webview1").vw.setHeight((int)((100d / 100 * height)-(90d * scale)));
views.get("topimg").vw.setLeft((int)(0d));
views.get("topimg").vw.setTop((int)(0d));
views.get("topimg").vw.setWidth((int)((100d / 100 * width)));
views.get("topimg").vw.setHeight((int)((50d * scale)));
views.get("btnsearch").vw.setLeft((int)((2d / 100 * width)));
views.get("btnsearch").vw.setTop((int)((5d * scale)));
views.get("btnsearch").vw.setWidth((int)((40d * scale)));
views.get("btnsearch").vw.setHeight((int)((40d * scale)));
views.get("btnback").vw.setLeft((int)((84d / 100 * width)));
views.get("btnback").vw.setTop((int)((5d * scale)));
views.get("btnback").vw.setWidth((int)((40d * scale)));
views.get("btnback").vw.setHeight((int)((40d * scale)));
views.get("titlelabel").vw.setLeft((int)((2d / 100 * width)));
views.get("titlelabel").vw.setTop((int)((50d * scale)));
views.get("titlelabel").vw.setWidth((int)((96d / 100 * width)));
views.get("titlelabel").vw.setHeight((int)((40d * scale)));
views.get("btnfarabar").vw.setLeft((int)((25d / 100 * width)));
views.get("btnfarabar").vw.setTop((int)((5d * scale)));
views.get("btnfarabar").vw.setWidth((int)((50d / 100 * width)));
views.get("btnfarabar").vw.setHeight((int)((40d * scale)));

}
}