package mpozn.amarzn.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laysal{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("mainpanel").vw.setLeft((int)(0d));
views.get("mainpanel").vw.setTop((int)(0d));
views.get("mainpanel").vw.setWidth((int)((100d / 100 * width)));
views.get("mainpanel").vw.setHeight((int)((100d / 100 * height)));
views.get("topimg").vw.setLeft((int)(0d));
views.get("topimg").vw.setTop((int)(0d));
views.get("topimg").vw.setWidth((int)((100d / 100 * width)));
views.get("topimg").vw.setHeight((int)((10d / 100 * height)));
views.get("titlelabel").vw.setLeft((int)((4d / 100 * width)+(8d / 100 * height)));
views.get("titlelabel").vw.setTop((int)((1d / 100 * height)));
views.get("titlelabel").vw.setWidth((int)((68d / 100 * width)));
views.get("titlelabel").vw.setHeight((int)((8d / 100 * height)));
views.get("btnsearch").vw.setLeft((int)((2d / 100 * width)));
views.get("btnsearch").vw.setTop((int)((1d / 100 * height)));
views.get("btnsearch").vw.setWidth((int)((8d / 100 * height)));
views.get("btnsearch").vw.setHeight((int)((8d / 100 * height)));
views.get("btnback").vw.setLeft((int)((86d / 100 * width)));
views.get("btnback").vw.setTop((int)((1d / 100 * height)));
views.get("btnback").vw.setWidth((int)((8d / 100 * height)));
views.get("btnback").vw.setHeight((int)((8d / 100 * height)));
views.get("grohlistview").vw.setLeft((int)(5d));
views.get("grohlistview").vw.setTop((int)((10d / 100 * height)+5d));
views.get("grohlistview").vw.setWidth((int)((100d / 100 * width)-10d));
views.get("grohlistview").vw.setHeight((int)((90d / 100 * height)-10d));

}
}