package simu;

import java.util.*;
/**
 * New type, created from HH on INTREPID.
 */
public class ListFrame {
	private java.awt.Button ivjButton1 = null;
	private java.awt.Button ivjButton2 = null;
	private java.awt.Panel ivjContentsPane = null;
	private java.awt.Frame ivjFrame1 = null;
	private java.awt.List ivjList1 = null;
	private java.awt.Label ivjLabel1 = null;
	private java.awt.TextField ivjTextField1 = null;
	private Vector Liste = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ListFrame() {
	super();
	initialize(true);
}
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ListFrame(Vector bvListe, boolean showIt) {
	super();
	Liste = bvListe;
	initialize(showIt);
}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G44001CA5GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E1349A8BF0D4559527B825B61D4208B6E8EAF1448E5568D825A3DA5A525129586298855B58E19C540C038EAA4E50CE26837606E417A484C8C8C882D9A01B8F049AC378D8B124CABEA81F00995982EABA7212FD593C4D5BCF5EFB1B5DA49B68B9773E7B766D666DA20EE6664CFD771CFB6F795CF34FB977EEC8451E39363C8D95046459087E77FE9EA10E7D0434ABE91D9CD7C81C447C073DB2720F7A82E82B
	21575364A425647E2AC268B7C3FF6DBEA729026FF272D05522FD7011044FEC20157F685EEB7069D0B81F0D343FC6AA01F68BC0AB60DCG99847997FA2BF5FC847ADB74EF043C8DA0C29E0EF9FB325A04DF290FA3F92C1DC14754FB93477481BE280FA92212ED5EF6D427E79D3C05DCFAFB27FDB3AEE31A47A1CB1F37D6EF89D9E3C3A36413819B980434778EFBBED4A5DF282D2DC7D2350F9541A73AB7209C8DD78A69B830A974995FA76A5A75F553665C89C629C94AF77E8646EF6906985F4BB70F643135CFF979
	5AA579CE12160F3879DE34EBB7FC7781E8791C67ABFBB94F0501BBC95F6E64BC3372F5DD73E3BC9BEE98495BC177ADDBC53B3696401E818C812481E482AC8528217EB2C37997349D7DDAD740678B78BB0241CE592D1C8D38C5993E5B5A002263BE9387820A1B68BE317FCA3305BC9CF28A1CD3F0CC370B046FE3774C5B49EC6D105DE621DB4516BAFB2E3EC609AD269B53CB8B21DE4D00BF89F099A082108E30504675FAD6B9FCAD3DCE79B5D1718B7279D0FFD0D0344E10A43BC5C5576F1CB719FE4BEDD3F5C3D8
	E743BDBDB70CFB3A8A3ED1A71B017F1C620F5BE3F847C6FA06298FE05F853405F66E8353A7381DDC93B3494A736F58B7DA59693ED67B650DCC1E4846C4BBD50751CE73G3F98A09BE08D72ACF41266C2EE27A67540575C7FDE130D585C9755CAE845082688277C2AA67887448B02A2897D322892FD4CEBAA4A5D9FB76FCD6A178340639351AFAA02A6857C30BA1CC35890150D49D54BF00CA2D22ACB7209404097A4FCDD228190D9D0357F865D02A646C6B0FA2106F69C95AE400A30GFCC78B515E4769996F016F
	A1GE6270FA208DB8C7DEC43AE5F5336C1FB869CC4145B5A3A258194C3D046B87F4950D654FDD474EB5DE2D0743B61C355E953C3C5B13B8C868C19EF22F87435AE90DCB570B3C3E58C8FEB182D4C696914AEE81AA27507B4713C28E9125F931BBF174A224639312A6BAB5238339A3E8B0AF8BCF78DA32E817AC70B383F3DF1097B5BB447EDE451AD75167E3660579576CBC54C4F02C5B1FFE36BFEC557CDAFB273DA603D14649CB594CDBDC7C7ADF0290071F8AAA90FFC8B1C6DBA560F452D79074DF16B725AC6CB
	7BE41E2F33AF52F5D8DE14F89E5F9C43333E96702545CE52DA4CFD6D5D51DAF686BB85D564276ED385F6D1F447BC0F0DF506137BDDD3585A6FDA4256FEF7A41C5A2F5A43D6FE45E8DD61ED748C7B7DA29550A09316EB5FF0216E12DF52AECAEE0F281D1746C5102198FD72A2BD03A7613B2F187BDF381E5B3769442D2475756D16F9216464E17B02E2E657A545B17BB2193AA3E8DFD196FDE0142EC0482FB17CE9FA3EF53C4A64FBC05804B6D1A49F18968F9DA9590432C53D95A6FE2B00872FED7269DA47FDF6E8
	E951F0106B7AE8E7B955152513DE531AEC5F8FCF20DCAED8FF88E05AA63E6FFF08A01D4798FD433B82324CCCCAF88DB5EB58DC9F25437C05GABG56EC62B12CBD0ABC8ACB1C249620350473A89EABB77168898406C3C197C6E81D7E3731D25CE3F1A4A4C278C64299F1CC0F416B4636A550AE88F2A8965B8B46510632E94D6E921457EF54D118DB986EC60ADB8646FB38001F293325661A0EFC7F00651E53BA31046DC1C6C962199A1F44F59781FE29217355F1334E9D2A9A9810E89A5177CB6C403CC56BC02FB0
	AA3448025F83766F77025D39ECAFFDE12E7B348DB576CDC9E4DB49E55A9C13ED553411BCA64B8793E65FE3388FA70A219D149495EA90379861766C1EC05E1853F434AE6FA36595F1728AE12D2D0D3AA02B1FB3072DEA6C633ABC2C7E1CA3976A6BDD5A4C6B4F8381741919501F8F30F8B337DFE7547A9C1D0C623DE058700F1EA81E05D15DA7747A6E10F9CFD7F9F7A75D53ECDD3E551B9377346AAA4ADA3845C99CDB0C3392ADB36DA95422D0EEF84063386D2ECE66B359F87E1E34F15920E56BFEB200F6EC05B5
	FBG5CGD1008C03C77115EB560CA7A216B5A33367933446DB846BAD87D83D056740BB6444DA32E0EBAC4FD5EF1D1A73D84DF8975D1F662D2CE64CB77077D1FC3B894FEA4977A89E7BBDC0FBE0AB0F8D03EE34E7C52913B414F2DD83D7B61B6C599B0068E55852F19577B520C89E494FF1CE0A039B009F8B93067BF4A0F9AE3AB0E01D0B7A862CF3D17FC06A9A48BB10ACD7B11B9C13D177A3250936BAC1715D250936FAEA185B4A853405G2CDE7A95BDA70EB20821E59C374E0738DE688F1571BD7DC5G4FC21A
	51FF087667997DE301E4B549758272302EFBDC5F9A8F2BBA0959FD580DF60B987DG6D0F99B2F44828F703D16F1D217DF4233FEC78FA6A3F6B8546E3F5D29ED6FAFD935B141325B27A3FAC0C85C2F0D08A3721DF3CAC1BEB05A57ABE2D0116456DDF06B1E78CC22086D7BD9F9487C88E1DD76E67713A977AEE0069G73G9E87E07CEE3E087E59950022828ED52F6097D9AD1093B0477A66F2A735F58F34174BB95FD7DAF02F9272C5E7G6B0B9E26G0BD7DF7A10C784664D8418DF4EEB319F04F86EEBBFF687D9
	5D67304CFD19779C32AFAEE7BA2FAC0F457023B770B70F8F7C057ABCC7051338AA9067711BED063A3E8883DA20E574B9F1F4BC730D44CC7FD39C9D65FDEE03193EBE0EEEB34DE73A3DD6E96836FEAEE91E157C9DA923026934D8EFF3BE4E346516AAF94C464FB7D964E45CA619696E8872FC1BDB41736DAE856D5D3A5DC9CE82DC86E0B16AE9368F5BD90CC2E078EC0A1FEE423358A5D0BC76E782ED6DF69E67EB87D126CB15CE228124814C83F83C12477C356ED6F20D7C668A84FBA0204B26904C4E58077ED46F
	BDD6406C3D53496DBDB4B407246D5A153C0E2A54FD280A350E6ACC9B36CC37059E7A1685349E00A0408C008C0085D5DC3FF6311969F742ADE99D1AA68C8C512A0BABF7C19243B1B49EC8261BB318285B122AD8ECDAD115ACBF4F227B39260A6D4FA6837FB30AAF2D0E61593E55D3BC76DB01B63B1A474867FC28B67BFE41746D71FDDB71BDF96C1C761D72506F2503496B04830356F542FB0356F542314154F7562EC12BBBAB7321370770FC64025DABF6B849D1009E004BBB387F3CB0D8477C4742F7CE40A59622
	B13EF2E91C576FBD4975DA6239765B58B39E3C877487B4AD604375B97EAF9E5C9BAD9024C806DB61C1FBC0C9B6243121BA7EA58F4699D99C24E8D62F9C8D729AA63A0357B04F0E709AA6E387773B1FA8E80FA550DF81308620E0A7545CBBB97DEECAEF05FEAFC090E0A6408386FD3FF2C91F0FF33F0EBF2C50F9DCEBDCCC06CE4AA3DF77572F4383E3EE51579837493021DDCA3E0F1B25EB6FE325C46FF8E0FF57D8D0E4389A09565003032AE878CB2DD4EA60CE293D81D542CBA92BA50420B9EFE569B6AA2801BA
	3486692F0E542632D70D790DE2D9B75AGFF5CB8E860EE5A04766B8C012F798F6A389F1F0E618E69382C536813AED078E389F259C1BE7F2DB06A53A3740BB26058DB49DF95334C9110816B5FF4E8997D9E4DCC4F5469D986FDDD580A1EE350DF9EB353F3F53A4341698FCE18692D8EC66FF570BA48D307391F666801002FC56A773504C6FDADC23F56A2C206E8EBBB9712E53DA2A2FA0EBEB802B15A8DEBC481E6BBF8BDE6D7D07FAE919FF9F8EE7BE1834FEDEB5F1BC3163F5AEA4758E4155BB29DECFF329D31BA
	097BEABE919C65E4CAAD015B275F5D4BD4DECB146C623ABD5B36FD6A9C3615045F79DF54783CEE1847F007A39C97B5567A67766A29EB99AE40666D9C67739698731E2F312801280B30B92F52378F7431553BF82DF3B735EF436E44DA67FE0AEF5D1DD86B3CBF42EB1DF6204D5D4DEB1DF4AF1E0702BDCED28B508D508790841881108130E08F0F6B8FCACD50DE903CFA450365C1C021119D102CB02631DD91609A4C62CE251A2A5E495A939F43F27670F8750A16EADE2EE9DEC92D5314070FC6D24D2B2D0D4DBB92
	B7EF4FF82AF9A7E1ECDFED3C2CEC5E9B93D673027A58695026DB4E3BF704985F8BCC5F7F99796EEB158B5FA98F66ABF7F9136702FB3C574E858FF92DF37AA35E29B97D37DE6B1C7E38571C53996EC9FDDD7455F3024FD877E9AF5EDF583D742C6097BC744D04657CF7D51E73B3EBF99DF00E4604AC682FB2F06BB5BEEE1D011B9EC6DC2D53C90EB8B96E53889FF75C40854778B837017B77B89F97B4F097E9AC188D7D79864E9625EF3750DFEA60C2D1BEB75B405DBA49672E83E0F768EE155F03CB6B1C2481209B
	408DB03D0E47ECD14358618B491A9414C53C67AC284339139AFF1F1ED747572B887175F281D7D26FA44DGBDGF2BDDF6F76300D5A9A829C34E975785F9215D8FA6AEB8CC6789AD9C04B81A8E8G59G3A8158F81AEA7471E74779F8B750A3G69G8B81329BB8CF7F847A4D2824CA783E4E643FF1024B1FEB0CF35037D98D1C13714989457824C5B91F42C6481FG6DGFDGD1G36666FA6791A990DBCD7F8D5FEFF4FEC647777F4B5717E7E1B387B7B9F55447BBB662998FD1D1AF8FF5F95B73FC5CB3C3F1769
	73191E33B52A279292D98E1E67B13FB7E7BF69CC7A5E1C534862D92E5E8E15E75A181EEDA12EE76BDE2E67D0A8D14F83F1FA5C96CA54D30853E3E9A8D14F4F63686EF0221EDF66195FA92A3EB06BF57CB1271D6745C43D0676B2FD343D315A0269159661FA4DB774FAB412DA2F5511C43DFE9AA7776E31C43DAE4651FB4792757A1FCEE7B22963DC261506CCF70E2716E959F822CC9DF1F45BC422CCDF4649747EC4B21918CFE5CCF01FE2B2BE9865B2D66F63B22E0CA64A38BFCE06ED13297D3EFBB2D146E76268
	231309B2FE95A7631FA7390CAC9786D256A7D6207F96C963FAAF68A56F6375517E51F7C8727A28D78984C5C59BE3D87A8B201E0FBF8AFDB399BE4067E41D770D230D49F8432D7BB414156CF7B91760A13C8E181532D63282B6AF1232D6CABE6F6168754EE33A9D8D633771D641CC2753BE22679FFF9F1784D99A27BFCF1E0701FE8F7BBF3E1F571D01B673F5E253C855E353218ED1C2FE0F682FFDECD1BF7EE8C3DFA748F6B3E5D4D44222B20878ED97E147EA01D24B8B943F9ED7E6B41A630A6BB3C752F8B93309
	4513F9CD093F4F3D5500BE3492706B1A388FF5B43C1BE29F4F7B84D95684F938436F46BD6D9C5344581B4D5F9BF0FD150F51090C363E8165761A667CFFGD0CB87888FD104116C8EGGC4A9GGD0CB818294G94G88G88G44001CA58FD104116C8EGGC4A9GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGGA68FGGGG
**end of data**/
}
/**
 * Return the Button1 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getButton1() {
	if (ivjButton1 == null) {
		try {
			ivjButton1 = new java.awt.Button();
			ivjButton1.setName("Button1");
			ivjButton1.setBounds(173, 176, 172, 23);
			ivjButton1.setLabel("�nderungen �bernehmen");
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
private java.awt.Button getButton2() {
	if (ivjButton2 == null) {
		try {
			ivjButton2 = new java.awt.Button();
			ivjButton2.setName("Button2");
			ivjButton2.setBounds(174, 206, 171, 23);
			ivjButton2.setLabel("�nderungen verwerfen");
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
private java.awt.Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(null);
			getContentsPane().add(getList1(), getList1().getName());
			getContentsPane().add(getButton1(), getButton1().getName());
			getContentsPane().add(getButton2(), getButton2().getName());
			getContentsPane().add(getTextField1(), getTextField1().getName());
			getContentsPane().add(getLabel1(), getLabel1().getName());
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
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Frame getFrame1() {
	if (ivjFrame1 == null) {
		try {
			ivjFrame1 = new java.awt.Frame();
			ivjFrame1.setName("Frame1");
			ivjFrame1.setLayout(new java.awt.BorderLayout());
			ivjFrame1.setBounds(18, 18, 362, 240);
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
 * Return the Label1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getLabel1() {
	if (ivjLabel1 == null) {
		try {
			ivjLabel1 = new java.awt.Label();
			ivjLabel1.setName("Label1");
			ivjLabel1.setText("Wert der Variablen");
			ivjLabel1.setBounds(175, 10, 104, 20);
			ivjLabel1.setVisible(true);
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
 * Return the List1 property value.
 * @return java.awt.List
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.List getList1() {
	if (ivjList1 == null) {
		try {
			ivjList1 = new java.awt.List();
			ivjList1.setName("List1");
			ivjList1.setBounds(8, 10, 142, 222);
			ivjList1.setMultipleMode(true);
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
 * Return the TextField1 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getTextField1() {
	if (ivjTextField1 == null) {
		try {
			ivjTextField1 = new java.awt.TextField();
			ivjTextField1.setName("TextField1");
			ivjTextField1.setBounds(174, 32, 173, 23);
			ivjTextField1.setVisible(true);
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
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize(boolean showIt) {
	// user code begin {1}
	getLabel1().setVisible(showIt);
	// user code end
	// user code begin {2}
	
	// getList1().add  !!!!!!
	
	// user code end
}
}