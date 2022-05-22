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
End Sub

Sub Globals
	Dim Cursor1 As Cursor
	Dim myFont As Typeface
	Private mainPanel As Panel
	Dim sv As SearchView
	Dim index As Object
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	
End Sub

Sub createSerch
	sv.Initialize(Me, "sv")
	Dim ind As List
	ind.Initialize
	Cursor1 = SQL1.ExecQuery("SELECT cname FROM child")
	For i = 0 To Cursor1.RowCount - 1
		Cursor1.Position = i
		ind.Add(Cursor1.GetString("cname"))
	Next
	Cursor1.Close
	index = sv.SetItems(ind)
	sv.SetIndex(index)
	sv.AddToParent(mainPanel, 1%x, 11%y, 98%x, 88%y)
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
	
	Activity.LoadLayout("laySearch")
	
	createSerch
	
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)
		
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
	End If
	Return True
End Sub

Sub sv_ItemClick(Value As String)
	Cursor1 = SQL1.ExecQuery("SELECT cid FROM child where cname = '" & Value & "'")
	Cursor1.Position = 0
	actSalViewer.cid = Cursor1.GetString("cid")
	StartActivity(actSalViewer)
	Activity.Finish
End Sub

Sub btnBack_Click
	Activity.Finish
End Sub
