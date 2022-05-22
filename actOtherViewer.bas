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

End Sub

Sub Globals
	Dim myFont As Typeface
	Private mainPanel As Panel
	Private titleLabel As Label
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

	Activity.LoadLayout("layotherviewer")
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)
	
	titleLabel.Text = Main.pageTitle	
	
	WebView1.LoadUrl(Main.pageUrl)

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
	End If
	Return True
End Sub

Sub btnSearch_Click
	StartActivity(actSearch)
	Activity.Finish
End Sub

Sub btnBack_Click
	Activity.Finish
End Sub