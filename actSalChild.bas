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
	Dim pid As String
End Sub

Sub Globals
	Dim Cursor1 As Cursor
	Dim myFont As Typeface
	Private mainPanel As Panel
	Private titleLabel As Label
	Private grohListView As ListView
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
	
	Cursor1 = SQL1.ExecQuery("SELECT pname FROM parent where pid ='" & pid & "'")
	
	Activity.LoadLayout("laySalchild")
	Cursor1.Position = 0
	titleLabel.Text = Cursor1.GetString("pname")
	
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)
	
	BuildItems_groh
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
	End If
	Return True
End Sub

Sub BuildItems_groh
	grohListView.Clear
	grohListView.TwoLinesAndBitmap.ItemHeight = 12%y
	grohListView.TwoLinesAndBitmap.Label.TextColor = Colors.Gray
	grohListView.TwoLinesAndBitmap.SecondLabel.TextColor = Colors.Black
	grohListView.TwoLinesAndBitmap.Label.Gravity = Gravity.RIGHT
	grohListView.TwoLinesAndBitmap.SecondLabel.Gravity = Gravity.RIGHT
	grohListView.TwoLinesAndBitmap.Label.TextSize = 16
	grohListView.TwoLinesAndBitmap.SecondLabel.TextSize = 15
	grohListView.TwoLinesAndBitmap.Label.Typeface = myFont
	grohListView.TwoLinesAndBitmap.SecondLabel.Typeface = myFont
	grohListView.TwoLinesAndBitmap.Label.SetLayout(15%x+20dip, 1%y, 75%x, 4%y)
	grohListView.TwoLinesAndBitmap.SecondLabel.SetLayout(15%x+20dip, 5%y, 75%x, 7%y)
	grohListView.TwoLinesAndBitmap.ImageView.SetLayout(10dip, 5dip, 17%x, 17%x)
	grohListView.Color = Colors.Transparent
	Dim gd As ColorDrawable
	gd.Initialize(Colors.RGB(240,240,240),0)
	grohListView.TwoLinesAndBitmap.Background = gd
	
	Cursor1 = SQL1.ExecQuery("SELECT * FROM child where pid='" &pid& "'")
	For i = 0 To Cursor1.RowCount-1
		Cursor1.Position = i		
		If Cursor1.GetString("ctype").Contains("جدول")  Then
			grohListView.AddTwoLinesAndBitmap2(Cursor1.GetString("ctype"),Cursor1.GetString("cname"),LoadBitmap(File.DirAssets,"table.png"),Cursor1.GetString("cid"))
		Else If Cursor1.GetString("ctype").Contains("نمودار")  Then
			grohListView.AddTwoLinesAndBitmap2(Cursor1.GetString("ctype"),Cursor1.GetString("cname"),LoadBitmap(File.DirAssets,"chart.png"),Cursor1.GetString("cid"))
		Else If Cursor1.GetString("ctype").Contains("نقشه")  Then
			grohListView.AddTwoLinesAndBitmap2(Cursor1.GetString("ctype"),Cursor1.GetString("cname"),LoadBitmap(File.DirAssets,"map.png"),Cursor1.GetString("cid"))
		Else
			grohListView.AddTwoLinesAndBitmap2(Cursor1.GetString("ctype"),Cursor1.GetString("cname"),LoadBitmap(File.DirAssets,"intro.png"),Cursor1.GetString("cid"))
		End If
	Next
	Cursor1.Close
End Sub

Sub grohListView_ItemClick (Position As Int, Value As Object)
	actSalViewer.cid = Value
	StartActivity(actSalViewer)
End Sub

Sub btnSearch_Click
	StartActivity(actSearch)
End Sub

Sub btnBack_Click
	Activity.Finish
End Sub
