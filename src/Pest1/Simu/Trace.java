package simu;

import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * Klasse Trace
 * Stellt die Datenstruktur fuer die Traces bereit.
 * Format:	Aktuelle States		= cStates
 *			Aktuelle Events		= cEvents
 *			Aktuelle Bvars		= cBvars
 *			wobei cStates, cEvents und cBvars alles Vectoren sind
 * Da die Traces nicht auf veraenderten Statecharts benutzt werden koennen muessen, enthalten die Vectoren
 * die Indexnummern und Namen derjenigen cStates | cEvents | cBvars, die true sind, d.h. aktiviert sind.
 *  
 * @param
 * @return
 * @author Helge Hauschild
 * @version V1.0 vom 18.02.1999
 * @version V3.1 vom 19.02.1999
 * @version V4.01 vom 19.02.1999
 *  
 */
 
public class Trace implements java.awt.event.ActionListener {
	Simu simuObject;
	TraceData myTrace;							// Erzeugt unseren Trace.
	int traceCounter = 0;						// Zähler der auf das zuletzt aktuelle TranceElement zeigt.
	Vector cStates = new Vector();				// Jeder Eintrag in diesen Vectoren zeigt fuer je einen Traceschritt auf einen
	Vector cEvents = new Vector();				// weiteren Vector, der alle zu dem Zeitpunkt aktiven States,
	Vector cBvars = new Vector();				// sowie die bvList und die seList enthält.
	Vector cExStates = new Vector();			// Ausbaustufe mit Entered- und ExitedList
	Vector cEnStates = new Vector();
	private Button ivjButton1 = null;
	private Button ivjButton2 = null;
	private Panel ivjContentsPane = null;
	private Frame ivjFrame1 = null;
	private TextField ivjTextField1 = null;
	private MenuBar ivjFrame1MenuBar = null;
	private Menu ivjMenu1 = null;
	private MenuItem ivjMenuItem1 = null;
	private MenuItem ivjMenuItem2 = null;
	private MenuItem ivjMenuItem3 = null;
	private MenuItem ivjMenuSeparator1 = null;
	private Label ivjLabel1 = null;
	private TextField ivjTextField2 = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public Trace(Simu arg1) {
	super();
	
	simuObject = arg1;
	initialize();
}
/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void actionPerformed(java.awt.event.ActionEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getMenuItem3()) ) {
		connEtoC1(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * connEtoC1:  (MenuItem3.action.actionPerformed(java.awt.event.ActionEvent) --> Trace.menuItem3_ActionEvents()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		// this.menuItem3_ActionEvents();
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
	D0CB838494G88G88GD308D5A6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E13CBB8BD0D4D716EC2DB363E61CDAE7E292931DAC5918AAEBA43BB1D20919F1E6C98D9B590949B20E3BCBF6CD45C95C0C13A22E3363DAEEADC99E4DC7D0C4D0B4A4C2ECA3A8AAE0B79F7904FF830222F68431B528BC688734B65DBD5D8F45003A675E7B4E6D570F57AD264CD0F56A3DFB4EBD771EFB6EB9671EFBDEA3646C8BCFC9B46488C2E20A207CD5A58AC2FF1DA018FB573F03384D47EC86C175379F
	A0DBF874CEB2BC53CCB6C358F51BA1CB287725C1BB8F5A3157EC060F61FD17B0B23A3096DE024C9386348F4DF7FE91FA1E7DC9B84F965AFE7D6B8CF8E6829881578AB04BC466FF776BDC85BF826DCC651DC0A20C1AEBA6FD1EE94ED561E315FE717094003E0076C91C507669873C8CB08BA01DCA36FDE7B5524D0F88368FBFCD5DCE66D171A1441B599CD9E56CA9889BD2089212048D49D9702C33DE9FEE70B9C647EAEBFB3C622094D6C6E4F835B90D7691048EE8E714931C25052CBB9A5A2B81B61621AC4BBF2F
	D3661A7578D330503DC1E53116B39964F23FACCB2317A731B98FCD601C1165B867F7A7481C71701EDCE1B364D5A0BE3B1160AB205D85E02FC0D95EBD0832ACF5BFA574FD96DC16498AA6C3F805DF160CA54B13BE21FB7CECB2590355C093AAED069C00EAG9B40B840DEE6DBC9C76059BCA0DB5C2323EED73347536234FBCF3B6D12935EEBEB0122604EC903EE2FDD90187D44B6976B4803B03BB250F6883035DD90F04FDBE74F936648E529A9BAEB4B497CBCF501B24652CA7F5A92AA095EFEC677B08E5E899F51
	8D437F0A621B0E7B71F5D5845FC97124ED87DA6CF154730C891425E7E23630267BE86A96BDD91EB52506D5B19966D679E5E1BA7EDEAA51F1B46057GECA673D55B8CA6GEBB56A78792472FB693853A5CBDE17686C9E9B7008DE39E54C6134CBDEC557459341F46D2C1E2A67CC2E0F62C9326E30EA260F940E2F2278B9AABC5353ACEA27243D90E899B5B6836B7F9DEA271E9A56BF090F7357949F264233F1BE26F8521E8D340DGCCD76FEFA13A3255C29CG188530GA09AE0CDAD6A6A0324E35334C72BCACF0C
	770564BD701446E52F5869724922EBD06A953D8EF140A97984254FBFA4931D8E8470C5D3BF9904B9CEC8AE49AB4A8E378BC697C7A558949F6B73DAF2B6696315A83547B1AA4184239E81477D1764DDE4BFC41FFC5EE397E5495F03515FCCA6B6F7C36C0591E1GF8B7FDC1F475BB9A2384F84F7B82751413CEF0B234E7FD01FA31256C0467A9B09249D9DB5B6A98A4E2085EDB680FAF3710B504C37FC80095GEB8104BA1BA1378E6DFD3D8D6DBDAC6F89E159A387F46DBD72E7B9297DF54C2E466B7C762E78BD1B
	8770D1GEBG04FA1B418850D40FE74B5916FE9D1B946A74EDB5D3119B5F57AA6FAB14E72E429779C5A0BF1B6B1A6EDC5B024C13295825BAA661D86439DDF926E8F8187E96162BE3D77F46C2DD7DC5F4FF1E5ADF2F682FDE9BAFDE5ACAFCE3AE60A381D655637EBE1CE2E4F65FA27AA4347433DEB0D2496E5FED56F7F66A8EE267B7A4175CAAF9A4179DDEFC0A1DBD161A6E7701A1B7374F9FD3BC09AD96B0D29F47BF1D1A4D70B0065A23103E0852C5D976BA8646E429DB12E507EB584F3F044A628B88DBBE4536
	AD74AC0C07B5A6B720ED14CD92DC81342BB92E1E62EC8D6AFEBF1A308559BF422B45D56B60C2811BE3D150B912F4F0DA1B185E9C5FEFCCD6EC04359F216DC93E4E7CC61B0E8DBDA8E0F39C27F3682D69C1G3303CDDB025B6487DB74EDB2A9CD5FA65353C25BDCF61A1E4DB1DAEE9A091303EE17CB22CE4349824B97577790199DAE07FC41E19F1664EE475792441A86E22F9F52D83CG5E17B6E04C3CF9GFD3E286DD141747EEE5DFCA52323BCB52B1159E0C1235F6719CC1F24931F171C52A8A845629EF3498C
	3F0F46F9856FCB223863DF1171CFCB2EB13332E7203FC3698A2ED316C67D787D8178B20EBF39ADD341F7CB20A2D1F6FB4DC8FB38D24DD34EF9FE53D883CFE39127940BD4FF2546121286A3CD2A0BC711F63519525A48EE7B0A8E779EB681AF0BEF7F3D0D2C3595F4E08370B4E2FC1B17CE68F8FEA9014D62F6BA59B681AD36116CC12AA5C72557190D6CEE328C1E0C5E5A2A2667B6B13A3589692F68526D1C5E5926260FAB74CCBE7FD81B9EFFA4675F94C00FD168B94DF8F6EF48A06BAF02F693003599573FAAFD
	17EA7DDDEE7775B1CF8F49CCB0AF59CA6EB1BE69CFE39224AD04F0CA3A25649E2FEC5D2921750A4EB1FFCE737AD6F25EB9D5E3B2DBFFCEA4F80F57B18AC787896E4A9AAFCD2857E08799712E47ECE1E2DFAABC5BC982E4C63F79E94BAE6AB7ACCD352A46E4F7C277247A3EB79346DC883082E08D4032963AB70DF98AFDB36032GE78270G4CE9E17327D9C83F5DADF807BA46F14594779B906C45E4BC0FCFE7296F0F42770E697AA601AE8B9B3F5F22BD0F8FE412F143GBF5B02FB5539CD3DD74DBE1FFB50C153
	BEC55F1B37297DD609F15B3642F3486175C1AEEF1746F90C4BA032123CD0C1AB7ABCA29C94266639EDF687B8AB9E7F24C57C916423F74BFACB1A423F400271696DCB6AFB2BEC581B3AAD088ED6ABEBCFD069A0767965C9CC1EFD3B756E017A72109800B2CD3462DE55355A8C8C7781474900E3634F117472FA7D71F954D26608E84371A278F80B0E565FF3BC4BCD9A21FA5C96C86DBB40BDAC7CD868F5C8B785DCC3C9BB0E3F3695EDE0D306FE4CCE48A06777F56E1B42F662ABB794FFE432C94D649EC2EB925744
	9BE229D3F48DC34C9B3886F3E31F17F665847491EF4A35355465D87D6421C93D9ACBDE9BFBB239EF5EA6F293BB28E8431A4249D2A237935AB3G668330BB59FFB5625DAE92F071ED984FF20E2AE3C1C13B4D50816084E0312037091CAB4AD9409ED649BB64760ECA76A2143C141E7B252AD361B058A8DD47F813F69DE1B0761CF6D687E23A989EA13E9874F491505F37ECEDD24689936689D166CA1AFBEAEDFD76DF6625C634B3BD2DEA775B7A489F17A7CD82A8F508B2F56CD8F76D33203143F8020DD1F1C29BBB
	36E4917DDB81EFBF01F6737576ECD56CB887BB8E1B8F919963C1D1E692339F25DD1C190276A3E03E56E289D5DB99BF91186B4EBA01E7CFEBB6594BF0E8AF83D885908F10510132AD493CE79D22EDBCC49DA22CB514DCDAA86E409C5750F6BFFCCD1CEF71FD7175F03E32767B619B69C05FF90656C5A621BD87A0020FB7F91B0C975D6157FB1C6ABD3EA3F0BF04CE2C1B7C027AE2CE2736EE92C371851D5A3A0935946BA695C01B5B09737FCF23F6BD891D01F3661E543B9BA7B5E979CAB56DBA8D1F12A3F7133A33
	A6B671404F4E7B75F754FE5D836348GF3G961E44F36054D10C2BD19C77E4934111F5EFBE09FADF51C456EDBA853187207F945A7393BBC93EB08E6DB9G91GB1G71GE9DDB6C3F1975A35E1075ED990E857418E827FFD174D796F3B08FCDDB0F69F9F7F6A8ED297C01F363AA18B92501FB332C87CF7FB9D438E973F1FD05C209B7AFB314EF3111E5DCE98F386G436D216764C2E8C771393A32035F233A33756FD19733756FD1D733C35F6D0733035D3318FC0FF179D6F119BFE9C3DCBC4745F0DC5AE944F552
	B3B58F5A5527519FBE39CD76396B34569F8ED0FC5FE92DBFAC1AC4FFF082AD160F7DB0BD77AA4EB01BD3E420365681383EB308DBDC0CBA1F643815A584978E6D48B368DF512564CC0F626D57E9BB0E37FBCB0355896E97489CDA9C1B437D4066503B6767D37CBF6D59C16D416713FC54A1DAC5D9D4E83F13F78BBA7766B3101088F8AFA8EECAD77A3449EE0B19470B5F6E5A259FAF046A33C467AF5FD66798890A8E0CE7B1A69C3C0BF1A26CAC46041F5EA5FB3F945A31G6B4E62DED80FCEB7368BD6B68F63FBFA
	CAAC3C97B05BECEA26B15C2A35591394DFE0555A6C4AA69E4301B6570AFE3DE0E7F03FDE34D35F2F976FA4F147679E738ECADA5FDD3AB317537C510C84E832E52C4F4B14DF963D4352945F0F217C0C960C7F57BBC357E15E5AC556BE4E5B6F5076ADEE970BEF123B4890A43540566D1186856B17C4C79E8B1E9D894A9EE5FD491E26AFB1A76C58CB6F346200646C0679DD430A8D16341258CA6B91BE344B3FEBA57A6B92C127465252D2EE2F26B632FE1858B1B88241BB0076D69B310B96096E844F5BB339CE5ADC
	F6A550337BE7BF0DE7ED8638839B5026765DA6F1220057728E53360D3797CF5ECF8D73FE014D712B20F3BC08FA9F5355CFEE1339C8FE5EA2FAD1FFC7D3697E5CF20F010E181FEF8A38CB4CD576B48A1EEC5FE30AF15F63209D8F10F18E4EF1002EF3F85F78FD89598B59A1531C59F90EFC4B5EC152G2CDFDDA07E8596AB9387320AAE0965A39F152A731167000F6CC3ACBC571EC31BFA28AB70DE466394D9164062CE4D9DB2FE827064F403EC5DF84F7E2BB13CE757FD7124905F1727FB4F0E78C9D9EAEBB7DB37
	3D5BFFDFB0AED91E44766B685D694621F0051F451BFFBCC4640A6CE671A61D63D7D2FC2C8A4F62D0940D1F249D8FB4EB8F1EFF43741C0C04F6CC8F5AE9FBF3209DDB1B836DB82E695B3763BF841D63C158B1BBE79E35E06E398A561EF11E6022836A0DA45EADCAE4F9B223EF8A2093BBA2F728D2816F246F6D61F671C138D07C43603FE168B92FFC93D21EEB5BA3D2426BE2147A4B3B25FB275AE60B1B54C93B1813B17B4CAAA67B7A9C0C91833066BC1E216B0BF9DEFD9E5716D722161D68718F02FAEDE7CA34EB
	AF8F2007855049FA5F89504D73A5DA5D5CCED46B6663CB6ABBF653AF7727A686510D291769242ED77B7B0EB70AF46AA4446D1DEC5DD54730A66669455CE11D1968E8A13423G62FAB17FFC167A7606DE6DD97EA2458B9734E7F9639DBC4B3300A6DF40FB4972FCA2D39C34B782E458EC068200D69B1E753DF9249E9E72BE526376F4C1B260D425654CBFDB6E22FF765B507FDEBDC4EA43CEE84FG188F30145362A8AD965A9BG32AE323AAA233DF2D76DD304C72DF39CEB3ABE6434695DB15F8A18A3DD4313A278
	3F2DCE87343E4EE27E453BE49C1A8B88F8A65C5CA7F09966052C176881334FCF77236F0E0CBCAE4C4A4F8FDABBB5DDE4EB37AA4F3C5C089472E4767030111CBDE1C01B8F90859087908F10FC89ED62493CE2E69324C459AC4B6260882D9E23C11002249FCD12A5E61BB3283F67DEE2361948ED768794DF2042B31B5DC37124ED825A1CCBE88BFFAC3EDF9D31353DC0FEBE2664F7EB738A6159ABDE532D361E8477E4B54E9EF1186FCD5DB16D3C1BAF796DA8632B2976475657D7C656E17C0A2DEF9B47DF2378A695
	1E2DFBBD75F9526E87DA74D7386EA57477F5C5FD587EB9ED139AA7EB375D196E3D0B0CF13F678E1B634A346778A6E79D5B1BFF4E8FFE3F7857FC7D7B456AFC7D3A415B7921737E7772753E3FB23FBCF1C0907EBC5FADD97DBA23C82FFEDD5707756D153AF4AB271FB99270AD2C0F516776E12C7F380050A3209D83908FA0DC065C70B27A75CB7BBE9728DF6B78F41BFD185C4D48AF11E4545DFCE370FDFA5AF86F5FAFC59849F7268137AC3BC7497808FF5EC8ECCDF6FBA81261DEB4921DF99D43A3B26B2A60FFEE
	24F591E90822190DBEC45CD4315707D56FF9458F2AAE915CBFAA3F55B958395AFD8C6BC3264B188B7F580C75A16BE50C8783E5E43FA721BD9BE08140B200D8CE77D07ABAE827DD31990AGBAGC62EA0FD23192C05702AE5083D9CBCF6CCAA735CEB9D4B388C5B4B489C5A5F235C6BB7CF53B90753857437405FA130DC4BD204F51A19D7B06FCCBF4CEB3CD7701EF5AD2BC8D0F236C177E829E3E034F46C46E829B8A017CAF0E430BB97F7F1B64EF70E60B81B2F20EF6DAB886EAB878B6E6DAB478A68F7DA30711E
	DB9E2526D1D1C06B35C3C3BE097BE4CDC19647F57A2CEE1F0354C2581DF67218BABE182EAAF7BBF84E3ECA6811E6E388FDCEDED5FFD7B76C21B507B170DD17D941FDF6410FABD3F0FB3FA4BE5E830BEBF7C8CEBB0F01FFBAC7E46FA2F99047AD4F8F68DB06F17119B2355CF1A08729E7A6DC453877438ABDFAD6BF52470F87444DFEC66F607441EAB53DCF21CFF27A19DABD7E597DE8439DC5534DC796742B6BF85107274B37B4006F21A35365DB2170A95FA6BFAF8CED4BE7461CCE650606B504849349C55640B8
	1B8132C42CED7C5ACC764FA2119FD1E08E7A7D824CC1B7D6BEAE2C7A3D097E5ECEAF87AD9219DCBD227F0E6417F3E56DEEE16A7D111A95775F55E5F8D71AA9227F46D4A0AECAC47F7DE8F76E54313809B13E3FBF0EFC1BB95F9BFBF56EEA54DC994F935548E39A605F086A91B7B200F179EA915E0142B96EBF8EA3AE02631EBA02382583F8477350BCB0FAC01BAF5F22785881ED3EECB0E33E3C9AE8CD03F86EAFAEA4FE3DG5AD1GEB8192G326C9073816AGFA6C9827DEADA071CEB7AF86A43B4453BC40ABBA
	DCB233475F160532C70F5DFF9E4C302B6D78350AD0FCF3D5FC9181FC114775782214BE2B6CD34F9F4677B755FAFC6B147E89C1792C75FAFCD912729BB9E9AA9F4B9F478E897C7D0DA27FFBAE79F90AB2768D337F7D2E6ABD4D7C6D67AA7B3F55B918BDADAB8CFE6E7D32705E675E0AC27D9CF1E561549C715F8A75F344B78B55B922F2AFD04675728FC38C3F2E10543CD8AD783468920769F78666231B4BB0472A1678B7C49AF37A206D613835957CDB9D47259C47FE619C77AA45C5C0FB9947192A10B70663DE20
	31A69E5A899C3737867B258D71FAA80DBF45502E63389575582FE388EB57BB4A313E6C84DC98C0B8C094401AA10C6F260A29F50A8BC709FD32B17E2F82479006618E81D0815083A083307E0F525C1B0D7776F1F2D715E133996D53D27FD8A72AF02CB9403B90A086E09DC05A884A73DAF5A07F45E3FE7E329A64AF027EF5GFDG13G73B97F1D7AC07E39A6BF7FBB75483F8C7A4782ECG48F2008E9D985BAB4B31A6E9F5E0CDF230DCDB13A4C21105B17AA3655A1A646EG7A72F2EDCD72E2C0CD72FF4B35B5C9
	64E7B26D2FC419E6F2194ED6061669AFAA35B2F5865017D4061669BFABC34BB4DA05B2C5F11966D505166935AA2DCCA782689FD5E9E5B287504D55DA19A69255B259EAF8BD194B745D1A50B2AD2D514A74C8G7D3D9A2DCC5B837414D72B1569C0001E529BD0A653B514292AA134CC0E862DCC0F855067B5045653EB8DDA194C0A4C6CBC28AD3F5F3A9A63BBD8794D783CD55F0C6FF24DB7634BEC38DFBE76BF2B45377F961E916F8BA932301DFE2EADE9176040123C3230C5796CAA8B296CC32EAC24732F3750BD
	600B2DACA41648425E935208E4F40C32DF229BA5078BFAF78F0EF89D32AC8B390154B3C801ABDE22D996BEEE11069CB021F19406A55DB8E32289737019056AEF95BD6772027E9EB07ABA4BDD622EEBFF8F7883DA3B4FF1C28CF4E2EE78661D92A1F84D34FBD4F4BAE551F93D59E5A7755316DB32642F337D68AE995F07FD94A223BDFE1748FDCD45737F83GD0CB878837345B743294GG08BCGGD0CB818294G94G88G88GD308D5A637345B743294GG08BCGG8CGGGGGGGGGGGGGGG
	GGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG6C94GGGG
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
			ivjButton1.setBounds(10, 10, 172, 23);
			ivjButton1.setLabel("Gehe im Trace einen Schritt");
			// user code begin {1}
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
 * Return the Button2 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getButton2() {
	if (ivjButton2 == null) {
		try {
			ivjButton2 = new java.awt.Button();
			ivjButton2.setName("Button2");
			ivjButton2.setBounds(10, 36, 172, 23);
			ivjButton2.setLabel("Gehe im Trace N Schritte");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjButton2;
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
			getContentsPane().add(getButton1(), getButton1().getName());
			getContentsPane().add(getButton2(), getButton2().getName());
			getContentsPane().add(getTextField1(), getTextField1().getName());
			getContentsPane().add(getLabel1(), getLabel1().getName());
			getContentsPane().add(getTextField2(), getTextField2().getName());
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
 * Return the Frame1 property value.
 * @return java.awt.Frame
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. *
*/


Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setMenuBar(getFrame1MenuBar());
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(190, 25, 241, 120);
			ivjFrame1.setTitle("Trace-Fenster");
			getFrame1().add(getContentsPane(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFrame1;
}
/**
 * Return the Frame1MenuBar property value.
 * @return java.awt.MenuBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuBar getFrame1MenuBar() {
	if (ivjFrame1MenuBar == null) {
		try {
			ivjFrame1MenuBar = new java.awt.MenuBar();
			ivjFrame1MenuBar.add(getMenu1());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjFrame1MenuBar;
}
/**
 * Return the Label1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Label getLabel1() {
	if (ivjLabel1 == null) {
		try {
			ivjLabel1 = new java.awt.Label();
			ivjLabel1.setName("Label1");
			ivjLabel1.setText("Befinde mich im Schritt");
			ivjLabel1.setBounds(17, 74, 134, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjLabel1;
}
/**
 * Return the Menu1 property value.
 * @return java.awt.Menu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Menu getMenu1() {
	if (ivjMenu1 == null) {
		try {
			ivjMenu1 = new java.awt.Menu();
			ivjMenu1.setLabel("Traces");
			ivjMenu1.add(getMenuItem1());
			ivjMenu1.add(getMenuItem2());
			ivjMenu1.add(getMenuSeparator1());
			ivjMenu1.add(getMenuItem3());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenu1;
}
/**
 * Return the MenuItem1 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem1() {
	if (ivjMenuItem1 == null) {
		try {
			ivjMenuItem1 = new java.awt.MenuItem();
			ivjMenuItem1.setLabel("Laden ...");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem1;
}
/**
 * Return the MenuItem2 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem2() {
	if (ivjMenuItem2 == null) {
		try {
			ivjMenuItem2 = new java.awt.MenuItem();
			ivjMenuItem2.setLabel("Speichern ...");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem2;
}
/**
 * Return the MenuItem3 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuItem3() {
	if (ivjMenuItem3 == null) {
		try {
			ivjMenuItem3 = new java.awt.MenuItem();
			ivjMenuItem3.setLabel("Beenden");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuItem3;
}
/**
 * Return the MenuSeparator1 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private MenuItem getMenuSeparator1() {
	if (ivjMenuSeparator1 == null) {
		try {
			ivjMenuSeparator1 = new java.awt.MenuItem();
			ivjMenuSeparator1.setLabel("-");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjMenuSeparator1;
}
/**
 * Return the TextField1 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextField getTextField1() {
	if (ivjTextField1 == null) {
		try {
			ivjTextField1 = new java.awt.TextField();
			ivjTextField1.setName("TextField1");
			ivjTextField1.setText("1");
			ivjTextField1.setBounds(189, 35, 24, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField1;
}
/**
 * Return the TextField2 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
TextField getTextField2() {
	if (ivjTextField2 == null) {
		try {
			ivjTextField2 = new java.awt.TextField();
			ivjTextField2.setName("TextField2");
			ivjTextField2.setText("0");
			ivjTextField2.setBounds(152, 75, 24, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextField2;
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
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	getMenuItem3().addActionListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}

	getFrame1().setVisible(false);			// Tracefenster unsichtbar machen

	// user code end
	initConnections();
	// user code begin {2}

	// user code end
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @param
 * @version V1.00 vom
 * @return java.util.Vector
 */
Vector loadTrace() {

	String DirName;
	String FileName;
	String LoadName;

	Vector result = null;
	
	FileDialog FD = new FileDialog(simuObject.getFrame1(), "Trace laden", FileDialog.LOAD);
	FD.setModal(true);
	FD.setVisible(true);
	DirName = FD.getDirectory();
	FileName = FD.getFile();
	LoadName = DirName+FileName;
	FD.setVisible(false);
	FD.dispose();
	
	try {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(LoadName));
		result = (Vector) in.readObject();
	}
	catch (Exception e) {
		e.printStackTrace();
	}	
	return result;
}
/**
 *
 * Diese Methode realisiert die Erzeugung eines TraceElements im Trace.
 * Außerdem wird am Schluß für die Anzeige auf dem Monitor gesorgt.
 * Die Methode ist vorbereitet für die Verlängerung des Übergabevektors auf 6 Elemente.
 *
 * Benutzt globale Objekte			:InList, seList, bvList die uebergeben werden.
 * Aufgerufen von					:Simu ??
 * Ruft auf							:
 * 
 * @param
 * @return
 * @version V1.00 vom
 */


void makeTraceStep(Vector arg1, Vector arg2, Vector arg3, boolean before) {
	int help;
	int counter = cStates.size();
	counter--;

	//System.out.println("Trace : makeTraceStep() : counter : "+counter);
	
	Vector inListClone = new Vector();
	Vector seListClone = new Vector();
	Vector bvListClone = new Vector();
	Vector enListClone = new Vector();
	Vector exListClone = new Vector();

	inListClone = (Vector) (arg1.clone());
	// enListClone = (Vector) (arg4.clone());											// ************************
	// exListClone = (Vector) (arg5.clone());												// ************************
	seListClone = simuObject.handcloneseList(arg2);		//(Vector) (arg2.clone());
	bvListClone = simuObject.handclonebvList(arg3);		// (Vector) (arg3.clone());

	if (traceCounter == 0){		// Das erste TraceElement ist einzufügen
		if (before == true){
			if (counter > -1){
				cStates.removeAllElements();		// Zuerst müssen natürlich alle
				cEvents.removeAllElements();		// Vektoren gelöscht werden, da wir ja von
				cBvars.removeAllElements();			// Vorne anfangen wollen. ( wenn welche da sind )
				//cEnStates.removeAllElements();	// ***********************
				//cExStates.removeAllElements();	// ***********************
			}
			cStates.addElement(inListClone); 
			cEvents.addElement(seListClone);
			cBvars.addElement(bvListClone);
			//cEnStates.addElement(enListClone);		// **********************
			//cExStates.addElement(exListClone);		// **********************
			
			// traceCounter++; darf hier NICHT erhöht werden !
		}
		else{
			cStates.addElement(inListClone); 
			cEvents.addElement(seListClone);
			cBvars.addElement(bvListClone);
			//cEnStates.addElement(enListClone);		// **********************
			//cExStates.addElement(exListClone);		// **********************
			traceCounter++; 
		}
	}
		
	else{						// Es ist NICHT das erste Element hinzuzufügen !
		
		if (before == true){	// das aktuelle Element soll überschrieben werden !!
			
			if(counter > traceCounter){		// Der "Overhead" muß gelöscht werden :
				//System.out.println("Trace : makeTraceStep() : "+(counter-traceCounter)+"zu loeschen");
				for (int i = counter; i >= (traceCounter+1); i = i - 1)
				{
					//System.out.println("Trace : makeTraceStep() : loesche : "+i);
					cStates.removeElementAt(i); 
					cEvents.removeElementAt(i);
					cBvars.removeElementAt(i);
					//cEnStates.removeElementAt(i);		// **********************
					//cExStates.removeElementAt(i);		// **********************
				}

			}
			// Das aktuelle Element soll überschrieben werden :
			// Dazu muß das vorhergehende erst gelöscht werden :
			//System.out.println("Trace : makeTraceStep() : überschreibe der Vorgänger mit :!");								
			cStates.removeElementAt(traceCounter); 
			cEvents.removeElementAt(traceCounter);
			cBvars.removeElementAt(traceCounter);
			//cEnStates.removeElementAt(traceCounter);		// **********************
			//cExStates.removeElementAt(traceCounter);			// **********************
			
			//	dann das neue einfügen :	
			cStates.addElement(inListClone); 
			cEvents.addElement(seListClone);
			cBvars.addElement(bvListClone);
			//cEnStates.addElement(enListClone);		// **********************
			//cExStates.addElement(exListClone);		// **********************
			
			//simuObject.printseList(seListClone);		// seList zu Prüfungszwecken ausgeben
			//traceCounter++;   Darf hier nicht erhöht werden 
		}
		else{					// es soll ein Element hinzugefügt werden !
						// ( es kann kein Overhead geben !!)
			cStates.addElement(inListClone); 
			cEvents.addElement(seListClone);
			cBvars.addElement(bvListClone);
			//cEnStates.addElement(enListClone);		// **********************
			//cExStates.addElement(exListClone);		// **********************
			traceCounter++;
		}
	}
	if (before == false){
		getTextField2().setText(String.valueOf(traceCounter));
		//  Die Anzeige auf dem Monitor :
		help = simuObject.handleMonitor.getScrollbar2().getValue();
		help++;
		simuObject.handleMonitor.getScrollbar2().setMaximum(traceCounter+1);		// Update des  unteren Scrollbars
		simuObject.handleMonitor.getScrollbar2().setValue(help);
		simuObject.handleMonitor.getTextField0().setText(String.valueOf(traceCounter));
		// Grafikausgabe :
		simuObject.handleMonitor.fillDrawFrameData();
		simuObject.handleMonitor.getDrawFrame1().repaint();

	}
	//System.out.println("Trace : makeTraceStep  : Wurde korrekt beendet !");
}
/**
 * Comment
 */
public void menuItem3_ActionEvents() {
//	System.out.println("Trace - MenuItem3: Action-Event gehoert");
/*	simuObject.getCBMI1().setState(false);
	simuObject.getCBMI1().setEnabled(false);
	simuObject.getMI3().setEnabled(true);

	getFrame1().dispose();*/
	
	
	/*	try {
		System.out.println("Trace - MenuItem3: Try-Versuch");
		this.finalize();
	}
	catch (Throwable t) {
		System.out.println("Trace - MenuItem3: Fehler gecatched");
	} */
	return;
}
/**
 * Benutzt globale Objekte			:
 * Aufgerufen von					:
 * Ruft auf							:
 * 
 * @params
 * @returns
 * @version V1.00 vom 23.02.1999
 * @version V4.00 vom 23.02.1999
 * @return boolean
 * @param ToSave java.util.Vector
 */
boolean saveTrace(Vector toSave) {
	Vector td = toSave;
	boolean isError = false;

	String DirName;
	String FileName;
	String SaveName;
	
	FileDialog FD = new FileDialog(simuObject.getFrame1(), "Trace speichern", FileDialog.SAVE);
	FD.setModal(true);
	FD.setVisible(true);
	DirName = FD.getDirectory();
	FileName = FD.getFile();
	SaveName = DirName+FileName;
	FD.setVisible(false);
	FD.dispose();
		
	try {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SaveName));
		out.writeObject(td);
		out.flush();
		out.close();
	} 
	catch (Exception e) {
		isError = true;
		// e.printStackTrace();
	}
	return isError;
}
}