Type=Activity
Version=7.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	Dim SQL1 As SQL
	Dim dir As String
	Dim cid As String
End Sub

Sub Globals
	Dim Cursor1 As Cursor
	Dim myFont As Typeface
	Private mainPanel As Panel
	Private titleLabel As Label
	Dim filepath As String
	Private btnFarabar As Button
	Private WebView1 As WebView
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	
End Sub

Sub SetTypeface(parent As Panel, t As Typeface)
	For Each v As View In parent
		If v Is Panel Then
			SetTypeface(v, t)
		Else If v Is Label Then
			Dim lbl As Label = v
			lbl.Typeface = t
		End If
	Next
End Sub

Sub Activity_Create (FirstTime As Boolean)
	' = = =  Load data base = = =
	dir=DBUtils.CopyDBFromAssets("amar96.db")
	If FirstTime Then
		SQL1.Initialize(dir, "amar96.db", False)
	End If
	Cursor1 = SQL1.ExecQuery("SELECT cname, filename, url FROM child where cid ='" & cid & "'")
	
	Activity.LoadLayout("laySalviewer")
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)
	
	Cursor1.Position = 0
	titleLabel.Text = Cursor1.GetString("cname")
	
	If Cursor1.GetString("filename").Contains("n") Then		
		Dim myhtml As String = "<!DOCTYPE html><html><head><style>.myImg{width:90%;display: block;margin-left: auto;margin-right: auto;padding:15px;}</style></head> <body> <img src=""file:///android_asset/data/img/" &Cursor1.GetString("filename")& ".jpg"" class=""myImg""></body></html>"
		WebView1.LoadHtml(myhtml)		
	Else
		filepath = "file:///android_asset/data/" & Cursor1.GetString("filename") & ".htm"
		WebView1.LoadUrl(filepath)
	End If	
	
	If Cursor1.GetString("url") <> Null Then
		btnFarabar.Enabled = True
	End If	
	
	Dim p As Phone
	p.SetScreenOrientation(-1)
	
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
	End If
	Return True
End Sub

Sub btnFarabar_Click
	If Connected Then
		ToastMessageShow("در حال دریافت اطلاعات",True)		
		WebView1.LoadUrl(Cursor1.GetString("url"))
	Else
		ToastMessageShow("ارتباط با سرور برقرار نیست. لطفا اتصال اینترنت دستگاه را کنترل کنید",True)
	End If	
End Sub

Sub Connected As Boolean
	Dim p As Phone
	Dim Response, Error As StringBuilder
	Response.Initialize
	Error.Initialize
	p.Shell("ping -c 1 8.8.8.8",Null,Response,Error)
	If Error.ToString="" Then
		Return True
	Else
		Return False
	End If
End Sub

Sub btnSearch_Click
	StartActivity(actSearch)
	Activity.Finish
End Sub

Sub btnBack_Click
	Activity.Finish
End Sub