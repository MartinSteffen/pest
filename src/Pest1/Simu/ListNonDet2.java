package simu;

/**
 * This type was created in VisualAge.
 */
import java.awt.*;
import java.util.*;
import java.lang.reflect.*;
import absyn.*;
import editor.*;

 public class ListNonDet2 implements java.awt.event.MouseListener {
	Vector inVector;	 	// bekommt einen Verweis auf den Vector mit den TransTabs,
									// unter denen die Auswahl zu treffen ist
									
	Simu simuObject;
	int listSize;			// bekommt die Anzahl der TransTabs des inVectors.
	boolean debug;	// debug-Variable
	int back;				// Hier wird der Rückgabewert schließich übergeben !
	int counter;			// Hier steht der Rückgabewert drin !
	
	private Button ivjButton1 = null;
	private Panel ivjContentsPane = null;
	private Dialog ivjDialog1 = null;
	private List ivjList1 = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ListNonDet2() {
	super();
	initialize();
}
/**
 * Konstruktor
 *
 */

public  ListNonDet2(Vector arg1,boolean arg2, Simu arg3, int arg4) {
	super();
	simuObject = arg3;
	listSize = arg4;
	inVector = arg1;
	debug = arg2;
	
	System.out.println("ListNonDet2; Konstruktor : Checkpoint 1: Gewählt ist jetzt : "+arg3.selectNonDetChoice);
	initialize();
	System.out.println("ListNonDet2; Konstruktor : Checkpoint 2");
	init();
	System.out.println("ListNonDet2; Konstruktor : Checkpoint 3");
	arg3.selectNonDetChoice = counter;
	System.out.println("ListNonDet2; Konstruktor : Checkpoint 4 : Gewählt ist jetzt : "+arg3.selectNonDetChoice);
	
}


// Es folgt eine Kopie von getDialog(), weil die REGENERATED wird !
/**
 * Return the Dialog1 property value.
 * @return java.awt.Dialog
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. 


public Dialog getDialog1() {
	if (ivjDialog1 == null) {
		try {
			ivjDialog1 = new java.awt.Dialog(new java.awt.Frame(),"NonDeterminism",true);
			ivjDialog1.setName("Dialog1");
			ivjDialog1.setLayout(new java.awt.BorderLayout());
			ivjDialog1.setBounds(113, 119, 538, 295);
			//ivjDialog1.setVisible(true);
			//vjDialog1.setModal(true);
			getDialog1().add(getContentsPane(), "Center");
			// user code begin {1}
			System.out.println("ListNonDet2; getDialog1 : Checkpoint ");
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	System.out.println("ListNonDet2; getDialog1 : Ende");
	return ivjDialog1;
}*/

/**
 * Comment
 */
public void button1_MouseClicked(java.awt.event.MouseEvent mouseEvent) {

	counter = getList1().getSelectedIndex();
	System.out.println("ListNonDet2; button1_mouseClicked() : Checkpoint 1");
	if (counter > -1){
		getDialog1().setVisible(false);
		System.out.println("ListNonDet2; button1_mouseClicked() : Checkpoint 2");
	}
	else{
			System.out.println("ListNonDet2; button1_mouseClicked() : Nichts selektiert !");
	}
	
	return;
}
/**
 * connEtoC1:  (Button1.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListNonDet2.button1_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.button1_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (List1.mouse.mouseClicked(java.awt.event.MouseEvent) --> ListNonDet2.list1_MouseClicked(Ljava.awt.event.MouseEvent;)V)
 * @param arg1 java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.MouseEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.list1_MouseClicked(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G04D9D5A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E134DA8DD0D45795F6DA27A5A91DA1ADC9CD4324A6352DE331352D1D18E892BBB1BF1D248DCDCD7323B392C5C5C17CDB0DC60C9701858DAAAA729350AC46A4D891C1C91BA1B4D57E84DC10400A4458898F76898B3B7B565D3782EAB3BD675EF75FBE9EEFD7C993E60E775DF36E6F395FB9771C3B121CF7A62727EC4FA1A4A51DA8FF7FCCA1247B6F04BC302E7CG67E546DA08762F98E8AF093F1886E5BE48
	A2F69413ECB23DBA936A25D017211E875F7B096DDC75D17888B2CF8E487226151D8FBD4F1E81BECF862D5FD9178565EE20F26059002200324903F5798AFF986A3B15EF24146D5076B5ECB349162F6147AA6D52C0168FB40736F934CE5FE6987861C0D300F650156D5A7711A20F29381BF43D7936F9974E236947A9EDB11B23C4A989D9130ECAC8A5195539D0D6378D76FD62F3387CD5D5D607CFEE155CF522FC1210C90BF1AD0F59B2E9DBB893289FD9023CFFDC407DAF043A89A8EB89DF5353072B15B9436FFDG
	94D698F4CD7DCB58DA4217845654785A2BA9EC4EA5F5FC4E671670B9EB6AF14E34B88BA9822A0C637C12334837C25D8EF4B30E2FE5597BFCAD3324874865C3415792994756B0ABAE30162867DFCDA920E77D73C1BC8B93487217DAC8A9D09B1087A8822810E22CF3G60CA4E744835124BA5394FF8BCB5CE3B37C5320BCE782E2A820942EB97FBA52F1DA8B8FA42D6EA309ECED317DA4670585EAE92FE76F5917711C87924B95DE0EFB93B8F1B67A8E344AC8D6CADED996AED9ABD43B54047FE581F71FFCF790D4B
	827CA34B11DFCD79D83F8C32394B381EA75E60EB315D08A0319D474C99C6EB19DEEE6EDF4E56B0E1F9E0ADCC4793DC2863E860BF83948B3489A8BF5EC2BE0A67BA1EBCF06AD6BAEEF24B2257ADB8BB7CBD9E41AB57789DCE3B68D5F49DDF97CC57367831FA5E2D6AA31E622EBFDE2F277514FFB3DE2F2746FA2E2788101900D87BA80A532E957AF1FEC1794E957AF14A4E72F1A600AC8E0869EA199BF5553892708EF493A892E8A6D04CCA2E2B040193370947B60D1ED85FDF8F22CF9007E52F50647649023BD76C
	943C8E2147A97A0852E656A06A34E7D43FC7291D74429CE7C5376895E4076406518517880762E3ED1E9A5C0BED3CA2155A9CAE91A6F0F9889F774F037BD19702CF3E60318B3298E84164FF9BC44CDD93BAE1C498G3EAD89282BD7290FDA837B4FCA60FA5A65C11E9D6AB79338DEBEF56E03729C00C4F4D6D555B9FAF19902F7045B630C8B58A7825A67A7F2FCA7F5F1FCCFA87AB119F3773B06781EB9BB479C1548F0B4B3B100EFB66EECBA6EC260A7A772BB2466C2D7885B47B58413A165A7061633B9BE8FB9C7
	EA8859EE85FB2164CCBFD3CFE9FDD13769BDC37DC4F79CB6972DE2EB2EDC25377F5FDEC52C5B006F810AD8454FAB5CE9E1B82E91FCA2C76EF9AF00CE3487CE0F353D433587F1FBCDF44BF522C7F45B6143270C93615A91407495C9455BBD140F3E2296C067D379D12E3D0C8FE3E8AD044B27D039A04BDEC70FDF96BBC4D9F6387B827D2369DAFC235C10CF416AB6FA37CD05BD4EDD453110D40F3CD8281BD45EE64A4BC95236EB2D8FF61E312B466241E4403BB5E67A024EE10417DBE144F80E874FE6AA98C9D5F9
	53A96FA358EFF7925FEF6559AECD3B714CF37BEBE921F324FE053D5C8EB1BC2CF6874766C637B1B6135D46585CAE05465ECE4988FBCC36D7C27F57AB395DA2B59ED5CCD8BC18E443B5BB5C8E7922435EA74A9D0E6BA2A1B3129037EB290F8D036F28A46E9B075E6536DFD2FF8FA9DFF540B08E49EABCE5B6A5B1ACE62E8E58BEDB53BE8F5A3E6894DD2014DA496F1699BF1F7AEF056FCB253C6C6E1BD0DACAEA7C32AC39ABCADD125FA71651FFEB1D0E5EC151DEB2A0DC934A04A1390CEA3B2C85E57578F954F27C
	78A796CD00DB5A43C67922C55B5F9AF4000F0FF69EADE76B38D9B1CC5701237D3F2BF0629863D9C385731989DE54DB8968339A48361A7B4CC88F4A799D27B84BDA4969E4C78E32D92B71BCD7DD48511C11F3B54BE3F62B72024EE0F2B67F8ADF96BDC638AB01CA5670797F64592F195FAAC903FE0F8D23879EBBDC4DC6FC0AD77DA20496A8B8A70EA8714163D77769E41D02539F08BB1E3E7A962EC5B3A6436D8C81799E2F43855781BAECE58F39755ABDD4439AF9DE464E316F987A00BA0760147AAA14319E2959
	CFED000512ED1AB1D97EF62CC11B1BF54318930126824D818ADB0BED961DADD2647301178814351635EBDC4B66E76DAE1CD75BC1BDF1ED20DD56BA562EF19D364B3C40732193AA6F9D4A7B2B1910075278DD5C122D4D8949F7CF985AA3D238B27E64F57A3B7883BF0EBB9778B16B781956F835E7FA466713FA9DB404D364EB3DDADBD57C1AF7A714D79CDE9F4465F6F1D875EBDEDCAB46F88ADB517BAF0F7D0B0C0DD96B6D8ED972722B3FF608DA01CD2A05B03391E0D02B3A32CE07B8C4389E96DF5266217276C2
	73DBC17490276CBFEDFDC08F5D107BA9FE5BE76C1BB67870BDE0D0456DE69F9C8344258C2BECBF6299B53726666C945CFDE00FBD8330DE5E46D7F1E0D49BB0F98CCB910E40E7EF818D8DC66F85F56B59DBG4B17F35AB295BDDEDE4F7362699D3876C828C7834DC3BE4DABB29BF9DE3190F8B9A60BE2AB339B3536E2853E87A89208594A617347219C172FD34F76C765A789668B6375377CBCB3F61ED656B5015CC766D6FCE85EE3F01E915FAEB2CFB5B17D4418826739E94F02142C6C85A9EC7FDF1E677BAF5AG
	BEED835FFF4EB96D7E43B7307DC7C34976F0A3379CF1BBCEEF2D646BA735F699BFF0A828DD4E5D4056BDFF035EAE5F9A22B98E7053B6F23B7CD25E2B314BF6C096008C3CA23735D2FFAA4329D2AF7727F31C926E47BB06F15F79B0E6A51095488EB4D11D63A17FAD736E7AE1433C1B61D0E8C0FDCF017166G4D5F48E33B02B6FDBE1E385122FE1357ADE3F4437265E29B0E17733AC50D8B997F98659F517059F97F0E5A8156ABC1B669F5BE3F33D1BFFF32EE4E7CCD236B2C5F75B17D8E696A553AFE8CBFAFD499
	58BA8BE488FF9BFC31D1EB6BB6988763FF994A30CD5C56DDAD5C56A7EF62396E4C913CC7DE063A89A8EBB35C47C0D620FE20704D7CAC4D570CFC6C68338C66E083B99053434473DFCDFFBF9853CF2F3F684DC67A930A760F559F86D20ADED22C7C0E1C37196B45F49DF949D04FFA036B20775AAE0D4D34C9F0532B7612BD04FEDC72BA7A9CEE4EBBC0F93D923477F25E67434163791EE163F83EFF58B81EF78F074EB52F8D870B77996EDF2476D574065E9E96D1FE699BFAFB68EB6076D08D3249C0ACAFAD27F7C8
	5A96086736F05E75F61A13C13DED8B371B8FECF87749EA3D025651D7327A439D215E9C46C3B8071E4766F861EB1B43A82FBCC879FF4C5DC34F456793FD54A86A84D9D0E44B658344A00FEA95DCA2613165CF9BF7A8ED6AE52936C24571ABA5B9E3F15CA6G9A990EF7B5F17B8E5F426F727FB463194D037AC2A0132AFBEFCB2877632C64007EAC4AB74354E3A341717B6408B1FE1F9DC13B71C9FEEF2F2847688BA3792AAC60B11AE45105AAE1ED96507E3260053CD25FFFB16D4FE4417AA70C843383266F596735
	7ABE29E245EC8D2523CA0D0EB8A9B15DF5341BE1353E1556C754F197B4A06EE355371925341E233EDD389A4673D6B3DEE2F3FCA7689CDF47FB89536B69A674FFCEE1C47203238CCBC6BCFE56260D159A959DFA12F92C741BA10C372FF885979CD107C76CA5762D586F2EF6FE3F45C05BB820B4609F812A5B4A634C9FDEC42C838AE484A59A865B6B3EA67DB9C6EF8D68F12E661B937303FF2136B3FF2B7E5DFDA16547EF553F3B77B67378B099E45DEFF2DFF8C78B770573D45E34D66445C3BDD9659D3472F6195B
	38BE1615A10674F73F04393915A9165979AB1FD21F8D7D6A00AEEF636FACF778F9BED37D717DA471F211E1BE93BD6D24D95E467490291498BF4EF81E7F2E76D0BBFFF70B81F9A6415FCC1736EB4F924F607CF6C24A20E4723C8E2D9CEDF0B5514A1BC649F13D9F26E965B7C649D19FCB147EECEFABF3553DED1DCCCA6F8A7EBBE69B0CC76F85250CEB08CEAF7FECC18A537B51A6FEFFC5031C7176B57333F88EF88CC7AFD15EA628E725F05EE24A437C9BF3F546F3B4F7A96DEE57DEF03C71596F371A795B636D76
	9B4F9BA5333949AD5C66AC303ECAA09B108C948944EC6E8F2DB82ED368911DD04ECC41DF3D8B69EF6FEC0CCAAB9FE3934832369713D2A0AB10930859D3CBBBE2B79C6AA94A79B1FEA765CF5270191DAD22D8477A9410E52696ABB15B56830897A7544300A6834D82FA990879616F5F40BCACE48CEB13BCD6385C1C1AF00A69FBCEFB283B400898C65FAE66986D6F3F17049FBC9874BD41144AB01013464A227C68F4AC595E6E4EC3BD56012C9BE8A2D094508C20E752787EDE3CD14A7607CF9FE7E4D9686D272FAA
	FCF3785091E023F3E5BE79E213564E96AA73E72797136AF43443234D16907BACC9AF56787C3EA14483FD5DD4F97F2EC59DAAAF2FCA4C7DD48B77A79321BF33F7D30B5E1F601BD5409F3C5F2A77A7E7C6493BDA757E646AA8791D2DFAFFF2BCD56BCF72AEE9F36D53CF941BB96E74FB1E236C799925C4DF4976F5494A77152C6EAB4C2A5F57CFD2346BFA449AFADF09D67D3E760C723307DA747BF2A87D99F6BB5A097AFDD9733D74532FAB968E6E275E7CC667E038CF4F8D9E13E6659A47247BF20DF32A7C5C503F
	11D8F20DFEA3E1B6FA2CG4F2152DCCC5A00E420B020A8B3374F5939078935CF835B2C3777E17C0B3F1A4BFC2E7B73026F6BE7F9377E2D7DD7F9780E5AA3015D39F0FC4E1F1907E7A3CB9E4AE43C0773D09F103E764B2C2942FFBC8F716C942FD0B60BC97A8E3FC7141824D7F215B9FADCE57EEB2EB221C7AE934173D4D5357A1DCEA5BAA1CAFCF23589FD76AC50C58CD03C195F9F8D4D98A3560A78E84E6D70FBC7384FB4FDF8AF193F225C3C17985BA149E076D71A913043G761E8D19639891BB47C9EF93F57F
	1772F12F1D4200612BF2B304E46CDD56A6742973FE91B2EF880E63D385414F3B2A605667DDDBC05F52611CECA39EA577E9AC206F92D72E78C495D7AD8559AA2F4957A6799C18B331BBF7A9BD276E8C7DDD1CC4794E8C7DDDBC54446F62E110AD4C60F771130596A8CFE796134BC09300A681C5834D83FA99E8CDA6370B1585A541F48CCC966CD25B708A8E374CF41C50C4D4FD1BB45F7FED7A66FD5ABD5F689CCCA7B105413170D2612D3190DBE8EC7B4B8A475AFEC221316D2FAD545A3E725B0CB22ED7FD34D0FE
	1BA944581B65FAAD02DB6823B9BA7BFF14BF6D44FDC52FA269B24960E100E26BE16FE96FDCDBFBD150776D79BB180D476F8858BA435A22AEDC67111D456474CE0E2993DD4703E18A6C3643A5B81D32609CBC6336238D570C48E2404747F56178BE5EC691AA3A6A42F58FE87A7CEFGD0CB878890F9CED79D8FGG08AAGGD0CB818294G94G88G88G04D9D5A690F9CED79D8FGG08AAGG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGG
	GD78FGGGG
**end of data**/
}
/**
 * Return the Button1 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton1() {
	if (ivjButton1 == null) {
		try {
			ivjButton1 = new java.awt.Button();
			ivjButton1.setName("Button1");
			ivjButton1.setBounds(406, 32, 56, 23);
			// user code begin {1}
			ivjButton1.setLabel("Take it");			
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton1;
}
/**
 * Return the ContentsPane property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(null);
			getContentsPane().add(getList1(), getList1().getName());
			getContentsPane().add(getButton1(), getButton1().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjContentsPane;
}

/**
 * Return the Dialog1 property value.
 * @return java.awt.Dialog
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */


public Dialog getDialog1() {
	if (ivjDialog1 == null) {
		try {
			ivjDialog1 = new java.awt.Dialog(new java.awt.Frame(),"NonDeterminism",true);
			ivjDialog1.setName("Dialog1");
			ivjDialog1.setLayout(new java.awt.BorderLayout());
			ivjDialog1.setBounds(113, 119, 538, 295);
			//ivjDialog1.setVisible(true);
			//vjDialog1.setModal(true);
			getDialog1().add(getContentsPane(), "Center");
			// user code begin {1}
			System.out.println("ListNonDet2; getDialog1 : Checkpoint ");
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	System.out.println("ListNonDet2; getDialog1 : Ende");
	return ivjDialog1;
}
/**
 * Return the List1 property value.
 * @return java.awt.List
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private List getList1() {
	if (ivjList1 == null) {
		try {
			ivjList1 = new java.awt.List();
			ivjList1.setName("List1");
			ivjList1.setBounds(52, 32, 263, 207);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjList1;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initialize the class.
 */

private void init() {

	TransTab listenElement = null;
	TransTabEntry tte = null;
	String target;
	System.out.println("ListNonDet2; init() : Checkpoint 1");
	// Hier wird die Liste mit den TargetPath-Namen gefüllt
	for (int i = 0; i < listSize; i++){
		System.out.println("ListNonDet2; init() : Checkpoint 2");
		listenElement = (TransTab) inVector.elementAt(i);
		tte = listenElement.transition;
		target = returnDottedPath(tte.tteTarget);
		getList1().add(target, i);
	}
	System.out.println("ListNonDet2; init() : Checkpoint 3");
	getDialog1().setVisible(true);
	System.out.println("ListNonDet2; init():Habe Fenster sichtbar gemacht ! Jetzt darf eigentlich keine Funktion mehr möglich sein !");

}
/**
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	getButton1().addMouseListener(this);
	getList1().addMouseListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	// user code end
	initConnections();
	// user code begin {2}
	
	// user code end
}
/**
 * This method was created in VisualAge.
 */
private void lightning() {
	
	boolean loop;
	TrList tempTL;
	ConnectorList tempCL;
	highlightObject ho;
	boolean debug = true;
	TransTab tt1;

	// Zuerst leeren wir den Highlightvektor und schieben die InList neu hinein.
	ho = new highlightObject(true);
	simuObject.selectHighlightObject();

	// Zu highlightende Transition anhand der aktuellen Position bestimmen.
	tt1 = (TransTab) (inVector.elementAt(counter));
	// Nun müssen die Transitionen und Connectoren noch gehighlightet werden :

	// Zuerst die Transitionen (**) :
		
	tempTL = tt1.tListe;						// Transitionsliste der 'abstrakten' Transition
	loop = true;
	while (loop == true) {			
		ho = new highlightObject(tempTL.head,Color.blue);	// HIER HIGHLIGHTEN WIR !!!
		
		if (debug == true){
			System.out.println("ListNonDet2 : lightning  : Habe Transition gehighlightet !");
		}
		
		// Dann pruefen, ob noch eine weitere Action gefunden werden kann
		if ( tempTL.tail != null){
			// Es ist noch mindestens eine weitere vorhanden ...
			tempTL = tempTL.tail;
		}
		else{
			// Es sind alle abgearbeitet => Schleife verlassen !
			loop = false;
				if (debug == true){
				System.out.println("ListNonDet2 : lightning  : Habe alle zugehörigen Transitionen gehighlightet !");
			}
				
		}
	}


	// Dann die Connectoren (*) :
		
	tempCL = tt1.conn;							// Connectorenliste der 'abstrakten' Transition


	while (loop == true) {
			
		 ho = new highlightObject(tempCL.head,Color.green);		// HIER HIGHLIGHTEN WIR !!!
			
		if (debug == true){
			System.out.println("ListNonDet2 : lightning : Habe Connector gehighlightet !");
		}
			
		// Dann pruefen, ob noch eine weiterer Connector gefunden werden kann
		if ( tempCL.tail != null){
			// Es ist noch mindestens eine weitere vorhanden ...
			tempCL = tempCL.tail;
		}
		else{
			// Es sind alle abgearbeitet => Schleife verlassen !
			loop = false;

			if (debug == true){
				System.out.println("ListNonDet2 : lightning : Habe alle zugehörigen Connectoren gehighlightet !");
			}
		}
	}
	ho = new highlightObject();			// Nachdem alle Objekte an den Editor uebergeben wurden, 
														// wird die highlight-Funktion gestartet.
		
}
/**
 * Comment
 */
public void list1_MouseClicked(java.awt.event.MouseEvent mouseEvent) {

	counter = getList1().getSelectedIndex();
	if (counter > -1){
		System.out.println("ListNonDet2() : list1_MouseClicked() : Jetzt solte gehighlightet werden !");
		lightning();
	}
	else{
		System.out.println("ListNonDet2() : list1_MouseClicked() : Es kann nichts gehighlightet werden!!");
	}
	return;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		ListNonDet2 aListNonDet2;
		aListNonDet2 = new ListNonDet2();
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of java.lang.Object");
		exception.printStackTrace(System.out);
	}
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseClicked(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getButton1()) ) {
		connEtoC1(e);
	}
	if ((e.getSource() == getList1()) ) {
		connEtoC2(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseEntered(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseExited(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mousePressed(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the MouseListener interface.
 * @param e java.awt.event.MouseEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void mouseReleased(java.awt.event.MouseEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * This method was created in VisualAge.
 * Benutzt globale Objekte			: ----
 * Aufgerufen von					: initialize();
 * Ruft auf							: ----
 * 
 * @return java.lang.String
 * @param arg1 absyn.Path
 * @version V4.00 vom 20.02.1999
 */
private String returnDottedPath(Path arg1) {
	String result = new String();
	Path tArg1 = arg1;
	while (tArg1.tail != null) {
		result = result.concat(tArg1.head);
		result = result.concat(".");
		tArg1 = tArg1.tail;
	}
	result = result.concat(tArg1.head);
	return result;
}
}