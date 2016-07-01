import java.awt.*;
import java.util.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;
public class applicatio_meca {
    static appli_meca applic_meca;
    public static void main(String[] args) {
	applic_meca = new appli_meca("Fenetre de choix de programmes");
	applic_meca.run();
    }
}
class appli_meca extends Frame{
    private int sx;private int sy;private MouseStatic mm;  
    final int top_demarre = 300,left_demarre = 50,bottom_demarre = 525,right_demarre = 875;
    final float pi=(float)3.141592652;
    String titre[]=new String[10];
    int ppmouseh;int ppmousev;Graphics gr;long toto_int=0;
    boolean relachee=false,pressee=false,cliquee=false,draguee=false;
    long temps_en_sec=0;int i_run;boolean creation_systemes;
    long temps_initial_en_secondes,temps_initial_en_secondes_prec,temps_minimum=1000;
    systeme systt;
    String d_ou_je_reviens;
    boolean fils_crees=false;
    private SimpleDateFormat formatter; 
    //private MouseMotion motion;
    Color orange_pale;
    int n_systemes;
    float part_d_entropie[]=new float[3073];//pour 256 particules avec spin.
    float cosphi_hasard[]=new float[3073];float sinphi_hasard[]=new float[3073];
    float cosphi_v_hasard[]=new float[3073];float sinphi_v_hasard[]=new float[3073];
    float rndm1 []=new float[3073];float rndm2 []=new float[3073];
    float sqrtrndm []=new float[3073];
    float log_factoriel[]=new float[6145];
    boolean toutdebut=true,run_applet=true,dejapaint=false;
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    String name;;
    Image image,image_comm,image_entropie;
    int imageh=0,imagew=0;

    appli_meca (String s){ 
	super(s);
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    if(systt!=null){
			    systt.elimination_reouverture_fenetres(false);
			    systt.dispose();
		    }
		    dispose();
		    System.exit(0);
		};
	    });
	titre[0]=" Influence des interactions entre particules sur la pression.";//0
	titre[1]=" Influence du rayon des particules ou de la temperature sur la pression.";//1
	titre[2]=" Evolution vers l'etat d'equilibre.";//2
	titre[3]=" Particules avec spin.";//3
	titre[4]=" Piston entre deux gaz de pressions differentes. Machine a vapeur.";//4
	titre[5]=" Verification de dS=dQ/T avec paroi chauffante.";//5
	titre[6]=" Melange de deux gaz de rayons differents.";//6
	titre[7]=" Melange de deux gaz de masses differentes.";//7eliminé
	titre[8]=" Particules composees. Reaction chimique.";//8
	titre[9]=" Gaz a haute densite. Diffusion d'un gaz dans un autre.";//9 
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
 	//motion=new MouseMotion(this); 
	//this.addMouseMotionListener(motion);
	setBackground(Color.white);
	formatter = new SimpleDateFormat ("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
	Date maintenant=new Date();orange_pale=new Color(140,90,0);
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	temps_initial_en_secondes_prec=temps_initial_en_secondes;
	creation_systemes=true;fils_crees=false;
	relachee=false;pressee=false;cliquee=false;draguee=false;
	float ph=0;
	for (int i=0;i<=2072;i++){
	    ph=(float)Math.random()*2*pi-pi;
	    cosphi_v_hasard[i]=(float)Math.cos(ph);
	    sinphi_v_hasard[i]=(float)Math.sin(ph);
	    if(cosphi_v_hasard[i]==0)
		cosphi_v_hasard[i]=(float)0.0001;
	    if(sinphi_v_hasard[i]==0)
		sinphi_v_hasard[i]=(float)0.0001;

	    ph=(float)Math.random()*2*pi-pi;
	    cosphi_hasard[i]=(float)Math.cos(ph);
	    sinphi_hasard[i]=(float)Math.sin(ph);
	    //if(i<30)
	    //	System.out.println(" cosphi_hasard[i] "+cosphi_hasard[i]+" sinphi_hasard[i] "+sinphi_hasard[i]);
	    //else
	    //	cosphi_hasard[1000]=0;
	    rndm1[i]=(float)Math.random();
	    rndm2[i]=(float)Math.random();
	    sqrtrndm[i]=(float)Math.sqrt((float)Math.random());
	}


	pack();setVisible(true);	
	setSize(1200,900);
	setLocation(0,0);
	gr= getGraphics();
	float log_c_2n=(float)Math.log(pi);
	for (int i=4;i<=2*2072;i+=2){
	    log_c_2n += (float)Math.log(2*pi/i);
	    part_d_entropie[i/2]=log_c_2n;
	}
	float log_fact=0;
	for (int i=2;i<=6144;i++){
	    log_fact += (float)Math.log((float)i);
	    log_factoriel[i] = log_fact;
	}
	
	gr.setColor(Color.black);gr.setFont(times_gras_24);

	name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_premiere_page.jpg";
	image=createImage(400,400);
	Graphics gTTampon=image.getGraphics();
	image=Toolkit.getDefaultToolkit().getImage(name);
	MediaTracker tracker=new MediaTracker(this);
	tracker.addImage(image,0); 
	name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_conseils.jpg";
	image_comm=createImage(600,400);
	gTTampon=image_comm.getGraphics();
	gTTampon.setColor(Color.black);
	image_comm=Toolkit.getDefaultToolkit().getImage(name);
	tracker.addImage(image_comm,1); 

	name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/calcul_entropie_meca_float.JPEG";
	image_entropie=createImage(450,350);
	gTTampon=image_entropie.getGraphics();
	image_entropie=Toolkit.getDefaultToolkit().getImage(name);
	tracker.addImage(image_entropie,2); 

	
	try {tracker.waitForAll(); }
	catch (InterruptedException e) {
	    System.out.println(" image pas arrivee?");
	}
	gTTampon.dispose();
	gTTampon=null;
	imagew=image.getWidth(this);
	imageh=image.getHeight(this);
	gr.drawImage(image,450,0,imagew,imageh,null);
    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h = Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return (h*3600+m*60+s);
    }    
    public void run(){
	int isleep=1;
	System.out.println(" Run ");
	relachee=false;pressee=false;cliquee=false;draguee=false;
	Date now;
    fin_de_programme:
	while (run_applet){
	    now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_secondes>temps_minimum){
		run_applet=true;
		break fin_de_programme; 
	    }
	    //if((peindre&&(!creation_systemess))||creation_systemes)
	    if(toutdebut){
		if(systt==null){
		    systt=new systeme(this,1,"fenetre de demonstration",9);
		    gr.drawImage(image,450,0,imagew,imageh,null);
		}
		if(!cliquee){
			systt.run();
		}else{
		    systt.gas[0].geschwind.dispose();
		    systt.gas[0].geschwind=null;
		    systt.dispose();
		    systt=null;
		    toutdebut=false;cliquee=false;
		    cliquee=false;
		    pressee=false;
		    creation_systemes=true;
		    d_ou_je_reviens="";
		    dejapaint=false;
		    System.out.println("&&&&&toutdebut "+toutdebut+" cliquee "+cliquee);
		}
	    }else{
		if(!dejapaint){
		    System.out.println(" vers paint,run_applet "+run_applet);
		    menu_principal_ou_fin();
		}
		if(d_ou_je_reviens!=""){
		    System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_systemes "+n_systemes);
		    n_systemes=0;
		    dejapaint=false;
		    menu_principal_ou_fin();
		}
		//System.out.println(" n_systemes "+n_systemes+" creation_systemes"+creation_systemes);
		if(creation_systemes)
		    demarrer_application();
		if(d_ou_je_reviens!=""){
		    System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_systemes "+n_systemes);
		    d_ou_je_reviens="";
		}
		
		//System.out.println(" avant bouge n_systemes "+n_systemes);
		if(systt!=null){
		    systt.run();
		    for(int i=0;i<systt.nb_de_gaz;i++)	
			if(systt.gas[i].i_found!=-1){
			    systt.lancer_les_nouvelles_fenetres();
			    systt.gas[i].i_found=-1;
		    }
		    if(systt.num_graphe_a_virer!=-1){
			systt.quel_graf[systt.num_graphe_a_virer].abandon();
			systt.barre_menus();
		    }
		    if(systt.num_spectre_a_virer!=-1){
			systt.quel_spectre[systt.num_spectre_a_virer].abandon();
			systt.barre_menus();
			//titre[20]="";
		    }
		    if(systt.num_param_comm_a_virer!=-1){
			systt.quel_comm[systt.num_param_comm_a_virer].abandon();
			systt.barre_menus();
		    }
		    if(systt.le_virer){
			d_ou_je_reviens=systt.command;
			if(d_ou_je_reviens=="Sortir du programme")
			    run_applet=false;
			else if(d_ou_je_reviens=="Revenir a la page principale")
			    suppression_systt();
			else if(d_ou_je_reviens=="Revenir a la page initiale avec infos"){
			    suppression_systt();
			    toutdebut=true;
			    eraserect(gr,0,0,1000,1600,Color.white);
			    gr.setColor(Color.black);
			}
		    }else if (systt.command!=""){
			for(int i=0;i<systt.nb_de_gaz;i++){
			    boolean tr=systt.gas[i].selectionne_actions_interaction();
			    if(tr){
				systt.command_prec=systt.command;systt.command="";
				systt.temps_action_prec=systt.temps_action;
				break;
			    }
			}
		    }
		}
	    }
	    i_run++;if(i_run==20)i_run=0;
	    //System.out.println("isleep");
	    try {Thread.sleep(isleep);}
	    catch (InterruptedException signal){System.out.println("catch ");}
	}
	System.out.println(" run_applet "+run_applet);
	systt.dispose();
	dispose();
	System.exit(0);
    }
    void suppression_systt(){
	systt.elimination_reouverture_fenetres(false);
	creation_systemes=true;
	dejapaint=false;
	cliquee=false;
	relachee=false;
	pressee=false;
	draguee=false;
	d_ou_je_reviens="je reviens de isyst "+systt.isyst;
	System.out.println("*systt.isyst "+systt.isyst+" n_ensembles "+n_systemes+" systt.command "+systt.command);
	n_systemes=0;
	systt.command="";
	systt.dispose();
	systt=null;
    }
    void demarrer_application(){ 
	//System.out.println("dem relachee "+relachee+" pressee "+pressee);
	if(cliquee){	    //	if(relachee&&pressee){
	    System.out.println("toto11");
	    int xi=ppmouseh;int yi=ppmousev;
	    cliquee=false;
	    if ((xi > left_demarre)&&(xi < right_demarre)){
		for(int i=0;i<=9;i++){
		    if ((yi > top_demarre+i*25)&&(yi < top_demarre+(i+1)*25)){
			n_systemes=1;
			int j=i;
			//if(i>=7)
			//    j=i+1;
			System.out.println("creation_d_un_systeme");
			creation_d_un_systeme(0,j);
			//systt.peint_systeme();
			relachee=false;pressee=false;
			creation_systemes=false;
		    }
		}
	    }
	}		
    }
    public void creation_d_un_systeme(int iii,int i_demarre){
	String str="Fenetre de demonstration";
	if(i_demarre>=0)
	    str=titre[i_demarre];
	System.out.println(" ensemble "+iii);
	systt=new systeme(this,0,str,i_demarre);
	//setVisible(false);
	//systt.setVisible(false);systt.setVisible(true);
    }
    public  void menu_principal_ou_fin(){
	System.out.println("deb dans peint, toutdebut"+toutdebut);
	if((!toutdebut)&&dejapaint&&run_applet)return;
	System.out.println("passage dans paint,dejapaint "+dejapaint);
	if(run_applet){
	    if(!dejapaint){
		dejapaint=true;
		eraserect(gr, 0,0,1000,1000,Color.white);
		gr.setColor(Color.red);gr.setFont(times_gras_14);
		System.out.println(" debut paint creation_systemes "+creation_systemes);
		gr.setColor(Color.red);
		
		//if(creation_systemes){
		//System.out.println(" top_cree "+top_cree);
		int ppv;
		gr.setColor(Color.blue);
		gr.setFont(times_gras_24);
		gr.drawString("Cliquez dans ce menu pour un ensemble de votre choix.Vous aurez une fenetre",left_demarre, top_demarre-150);	      
		gr.drawString("principale avec des menus, et d'autres fenetres, que vous pouvez deplacer",left_demarre, top_demarre-125);	      
		gr.drawString("ou eliminer et  dont la liste est consultable en cliquant sur le logo Java ",left_demarre, top_demarre-100);
		gr.drawString("dans la barre de programmes. Vous pourrez aussi demander differents graphes",left_demarre, top_demarre-75);	      
		gr.drawString("au cours du temps ou des 'spectres'. ",left_demarre, top_demarre-50);
		gr.drawString("Pour quitter, cliquez sur le petit rectangle rouge en haut a droite. ",left_demarre, top_demarre-25);	      
		
		gr.setColor(Color.red);
		paintrect_couleur(gr,top_demarre,left_demarre,bottom_demarre,right_demarre,Color.red);
		int j=0;
		for(int i=0;i<=9;i++){
		    j=i;
		    //if(j>=7)
		    // j=i+1;
		    gr.drawString(titre[j],left_demarre,top_demarre+22+i*25);
		    drawline_couleur(gr,left_demarre,top_demarre+25+i*25,right_demarre,top_demarre+25+i*25,Color.red);
		}
		//}
	    }
	}else{
	    System.out.println("erase g ");
	    eraserect( gr,0,0,1000,1000,Color.white);
	    gr.setFont(times_gras_24);gr.setColor(Color.black);
	    if(temps_en_sec-temps_initial_en_secondes>temps_minimum){
		for(int i=0;i<20;i++)
		    gr.drawString("TEMPS MAXIMUM EXPIRE",100,100);
	    }
	    for(int i=0;i<20;i++){
		gr.drawString("FIN DU PROGRAMME",100,150);
	    }
	    dispose();
	}
    }
    void drawline_couleur(Graphics g,int xin, int yin, int xfin, int yfin, Color couleur)
    {
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void paintrect_couleur(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.drawRect(x,y,width,height);
    }    
    void remplisrect(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.fillRect(x,y,width,height);
    }    
    
    void paintrect(Graphics g,int top, int left, int bot, int right)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(Color.black);g.drawRect(x,y,width,height);
    }
    void paintcircle(Graphics g,int xx, int yy, int rr){
	int x,y,r;
	x=xx;y=yy;r=rr;
	g.setColor(Color.blue);g.fillOval(x,y,r,r);
    }
    
    
  //   void eraserect(Graphics g, int top, int left, int bot, int right)
  //{int x,y,width,height;
  //x=left;y=top;width=right-left;height=bot-top;
    //g.setColor(Color.white);g.fillRect(x,y,width,height);
  //}
    void eraserect(Graphics g, int top, int left, int bot, int right,Color couleur){
	//System.out.println("erase");
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.fillRect(x,y,width,height);
    }
    void paintcircle_couleur (Graphics g,long x,long y, long r,Color couleur){
	g.setColor(couleur);g.fillOval((int)(x-r/2),(int)(y-r/2),(int)r,(int)r);
    }
    void peint_cercle_couleur (Graphics g,int x,int y, int r){
	for (int i=0;i<=r;i++){
	    float a=(float)r*r-i*i;
	    int j=(int)Math.round((float)Math.sqrt((float)(r*r-i*i)));
	    g.drawLine(x-j,y+i,x+j,y+i);
	    if(i!=0)
		g.drawLine(x-j,y-i,x+j,y-i);	
	}
    }
    public void	traite_click(){
	//System.out.println("entree traite_click ");
	//if(cliquee){
	    Date maintenant=new Date();
	    temps_initial_en_secondes=temps_en_secondes(maintenant);
	    //}
	toto_int=temps_initial_en_secondes_prec;
	if(cliquee){
	    if(temps_initial_en_secondes<temps_initial_en_secondes_prec+2){
		cliquee=false;pressee=false;relachee=false;
	    }else
		temps_initial_en_secondes_prec=temps_initial_en_secondes;
	}
	if(cliquee&&!toutdebut&&systt!=null){
	    cliquee=false;pressee=false;relachee=false;
	    systt.le_virer=true;
	    systt.command="Revenir a la page principale";
	}
    }
    class MouseStatic extends MouseAdapter{
	appli_meca subject;
	public MouseStatic (appli_meca a){
	    subject=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_cyl[icylindre].nb_el_ens "+ens_de_cyl[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_cyl[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
	    System.out.println("relachee "+relachee);
	}
    }
}
//class canevas extends Canvas{
//}
class systeme extends Frame implements ActionListener{
    static final int 	top_syste = 0;
    int 	left_syste = 0;
    systeme ruf;String totoString="";
    int 	bottom_syste = 600;
    int 	right_syste = 700;
    boolean sortie_du_potentiel=false,un_des_2_osc_harm=false;
    boolean particules_composees=false,second_passage=false;
    boolean graphe_energie_des_spins=false;
    String pas_possible=" ";
    boolean graphe_energie_part_compos=true,le_virer=false;
    int voir_parametre=1000;
    boolean graphe_energie_cinetique=false;
    String st1_energie=" ",st2_energie=" ",st3_energie=" ";
    String st1_dist_carr=" ",st2_dist_carr=" ";
    float proba_maxwell[]=new float[101];float n_theor_somme;
    float proba_maxw[][]=new float[2][101];
    final String str_menus0[]=new String [33];
    final String str_menus_bis0[]=new String [33];
    final static float portee[]=new float [4]; 
    final static float profdr[]=new float [4]; 
    boolean calcul_hypervolu=false;
    final int 	right_pression = 80,bottom_pression = 80,right_temperature = 140,bottom_temperature = 62,right_nTsp = 100,bottom_nTsp = 98,bottom_libre_p = 116,right_libre_p = 100;//right_entropie = 60;
    final int bottom_n_part = 134,right_spins = 120,bottom_spins = 152;//bottom_entropie = 134,
    int n_parametre_a_voir=-1,n_parametre_a_voir_prec=-2;;
    final int left_rect=450,top_rect=100,width_rect=300,height_rect=300;
    final Color gris_pale=new Color(81,81,81);
    final float racine_de_2=(float)Math.sqrt(2.0);
    final float pi=(float)3.14159265;int dernier_i_tr=-1,i_tr_aide=-1;
    graphes quel_graphe=null;
    graphes quel_graf[]=new graphes [8];
    fantome quel_spectre[]=new fantome [6];
    commentaire_aide quel_comm[]=new commentaire_aide[4];
    String st_graf[]=new String [8];
    String st_spectre[]=new String [6];
    String st_comm[]=new String [4];
    graphe_pression graf_pression;
    float mariotte0=0,mariotte1=0,moy_theor=0;
    graphe_entropie graf_entropie;
    graphe_dS_sur_dQ_sur_T grp_dS_sur_dQ_sur_T;	
    graphe_distance_carree_moyenne dist_carr_moy;
    spectre_energies sp_en_cin;
    spectre_nombre_de_particules_composees sp_nb;
    spectre_positions_moments sp_pos_mom[]=new spectre_positions_moments [2];
    graphe_energies grp_en_tot;
    String st_grp_en_tot="energies f.de t";
    couleur farbe[]=new couleur[16];
    boolean machina_vapore=false,vient_de_cliquer=false,vas_y=false;
    double a_trinome=0,bp_trinome=0,c_trinome=0,delta_trinome=0,sqrtdelta=0;
    int num_chaud=0;
    point v_ds_cm_tot[]=new point[3];int index[]=new int[3];
    point point_zer0,d_impact;boolean totologic=false,attention_aux_bords=false;
    point dist_au_cercle,posit_au_cercle,point_collision;
    boolean avant=false,apres=false,barre_menus_faite=false;
    float coco=0,cucu=0,cece=0,caca=0,cici=0;int toto_int=0;
    double dodo=0,dudu=0,dede=0,dada=0,didi=0;
    float impulsion_paroi_mobile_avant=0,travail_frottement_avant=0,d_pos_paroi_avant=0;
    float temps_collision=0,d_momx_part_event=0,d_energie_x_part_event=0,energie_x_part_event=0,energie_x_part_avant=0,energie_x_part_event_sec_pass=0;
    int ppmouseh;int ppmousev;private MouseStatic mm;
    //int ix_particule_min_max[][]=new int[128][16];
    boolean relachee=true,pressee=false,cliquee=false,draguee=false;
    float energ_init[]=new float [2048];
    float n_moyenne[]=new float[7];
    long n_event=0,n_event_stop=0;int n_event_calculs=0;int nb_evts_sans_calculs=20;
    int dx_pt_souris=0, dy_pt_souris=0;int i_nucl=-1;
    int  dernier_i_trouve=-1;
    static Graphics grp_c;
    int rayon_grosse_particule=10,rayon_vitesses=150;
    float surface_moments=pi*rayon_vitesses*rayon_vitesses;
    String st_bandeau="";int bandeau_x=0,bandeau_y=0;
    boolean appel_init=true;point dfvt;
    long temps_courant=0,temps_courant_prec=0;
    long temps_action=0,temps_action_prec=0;
    //static Graphics grp_c_clone[]=new Graphics[6];
    float d_temps=(float)0.02;
    float travail_frottement=0;
    int isyst=-1;
    boolean desactiver=false;
    boolean avec_amortissement=false;
    appli_meca appelant;point toto,toto1,tutu,tata,titi;
    boolean fin_gerelesmenus_avec_souris;
    boolean retour_traite_commande=false,stopper=false;
    String command="",command_prec="iii";
    int i_demarre;int index_courant_commentaire=0;
    gaz gas[]=new gaz[2];int nb_de_gaz;
    boolean paroi_mouvante=false;
    float d_pos_paroi=0,d_pos_paroi_event=0,d_imp_paroi_totale=0,impulsion_paroi_mobile=0;
    float vit_paroi=0,vviitt_paroi=0,vit_paroi_avant=0,masse_paroi_mouvante=0;
    float k_paroi=0;
    point r1_cm_essai,v1_cm_essai,r_cm_essai,v_cm_essai;
    float dt_essai=0,d_pos_2_essai=0;
    aider a_l_aide_svp;
    explications explications_bitte; Image image_explications_noires,image_explications_rouges;
    Graphics gTampon,gTampon0;Image crop,crop0;
    Graphics gTampon_chaud,gTampon_chaud0;Image crop_chaud,crop_chaud0;
    Graphics gTampon_part [][]=new Graphics [16][6];
    Image crop_part[][]=new Image [16][6];
    parametre paramt;parametre_entropie paramt_entropie;
    int num_graphe_a_virer=-1,num_spectre_a_virer=-1,num_param_comm_a_virer=-1;String st_open_graphe_ou_spectre_ou_comm="";
    boolean pendant_construction_syst_ou_gaz=true;

    public systeme(appli_meca a, int isyst1, String s,int i_demarre1){
	super(s);
	appelant=a;
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    le_virer=true;
		    command="Revenir a la page principale";
		};
	    });
	for(int i=0;i<33;i++)
	    str_menus_bis0[i]="";
	st_graf[0]="energies f.de t";
	st_graf[1]="graphe de la pression";
	st_graf[2]="toto";
	st_graf[3]="graphe de dS/(dQ/T),entropie des moments";
	st_graf[4]="variation relative de l'entropie en f. de t";
	st_graf[5]="distance carree moyenne au centre, f.de t";
	st_graf[6]="toto";
	st_graf[7]="toto";
	st_spectre[0]="spectre des energies autour de l'exponentielle";
	st_spectre[1]="spectre du nb de particules par zone";
	st_spectre[2]="spectre du nb de particules par zone de moments";
	st_spectre[3]="spectre du nombre de particules composees";
	st_spectre[4]="espace des moments, gaz 0";
	st_spectre[5]="espace des moments, gaz 1";
	st_comm[0]="Comment sont calcules les parametres physiques.";
	st_comm[1]="Comment est calculee l'entropie";
	st_comm[2]="Explications";
	st_comm[3]="Commentaires";

	str_menus0[0]="multiplier par 2, max a 2048";//au nb de particules
	str_menus0[1]="diviser par 2";
	str_menus0[2]="ajouter 1 pixel";//au rayon des particules
	str_menus0[3]="retrancher 1 pixel";
	str_menus0[4]="multiplier par 2, maximum à 4";
	str_menus0[5]="diviser par 2, minimum à 0.25";
	str_menus0[6]="reinitialiser avec toutes les particules de meme energie";
	str_menus0[7]="reinitialiser avec une distribution uniforme";
	str_menus0[25]="reinitialiser les deux fenetres en meme temps";
	str_menus0[8]="permettre aux particules de tourner sur elles-memes";
	str_menus_bis0[8]="ne pas permettre aux particules de tourner sur elles-memes";
	str_menus0[9]="paroi centrale mobile";
	str_menus_bis0[9]="paroi centrale immobile";
	str_menus0[10]="multiplier par 2";//l'energie des particules
	str_menus0[11]="Inerte(s)";
	str_menus_bis0[11]="Radiante(s)";
	//str_menus0[12]="mettre un amortissement de la paroi centrale";
	str_menus0[12]="";
	str_menus_bis0[12]="enlever l' amortissement de la paroi centrale";
	str_menus0[13]="Diminuer facteur 2";
	str_menus0[14]="Principe de la machine a vapeur.";
	str_menus_bis0[14]="toto";
	str_menus0[15]="diviser par 2.";//l'energie des particules
	str_menus0[16]="recommencer dans les memes conditions";
	str_menus0[17]="entropie f. de t";
	str_menus0[18]="microboules de petanque";
	str_menus0[19]="pas d'interaction";
	str_menus0[20]="energie totale f. de t";
	str_menus0[21]="plan des impulsions des particules";
	str_menus0[22]="spectre des energies autour de l'exponentielle";
	str_menus0[23]="0.99995";
	str_menus0[24]="spectre du nb de particules_composees";
	str_menus0[25]="pression f. de t";
	str_menus0[26]="plan x,y ";
	str_menus0[27]="plan px,py";
	str_menus0[28]=" raideur 4., portee 6 pixels";
	str_menus0[29]=" raideur 8., portee 6 pixels";
	str_menus0[30]=" raideur 16., portee 6 pixels";
	str_menus0[31]=" raideur 64., portee 4 pixels";
	str_menus0[32]="Augmenter facteur 2";//de la paroi
	portee[0]=6; 
	portee[1]=6; 
	portee[2]=6; 
	portee[3]=4;
	profdr[0]=(float)4.; 
	profdr[1]=(float)8.; 
	profdr[2]=(float)16.; 
	profdr[3]=(float)64.;
	System.out.println(" debut syst ");	

	isyst=isyst1;
	if(isyst==1){
	    //left_syste=550;
	    //right_syste=900;
	    left_syste=0;
	    right_syste=400;
	    bottom_syste = 800;
	}else{
	    left_syste=0;
	    right_syste=700;
	    bottom_syste = 600;
	}
	i_demarre=i_demarre1;
	System.out.println(" i_demarre "+i_demarre);
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	//masse_paroi_mouvante=(float)200.;
	masse_paroi_mouvante=(float)400.;
	//	k_paroi=1/d_temps;
	k_paroi=0;
	point_zer0=new point(0,0);d_impact=new point(point_zer0);
	point_collision=new point(point_zer0);
	for (int k=0;k<7;k++)
	    n_moyenne[k]=0;
	r_cm_essai=new point(point_zer0);v_cm_essai=new point(point_zer0);
	r1_cm_essai=new point(point_zer0);v1_cm_essai=new point(point_zer0);

	for (int j=0;j<3;j++)
	    v_ds_cm_tot[j]=new point(point_zer0);
	for (int i=0;i<3;i++){
	    farbe[i]=new couleur(255,60,i*80+15);
	    farbe[i+3]=new couleur(i*80+15,255,60);
	    farbe[i+6]=new couleur(60,i*80+15,255);
	    farbe[i+9]=new couleur(255,255,i*80+15);
	    farbe[i+12]=new couleur(i*80+15,255,60);
	}
	farbe[15]=new couleur(0,0,0);
	for (int i=0;i<16;i++)
	    farbe[i].print(" i "+i+" farbe[i] ");
	pack();setVisible(true);	
	setSize(right_syste-left_syste,bottom_syste-top_syste);
	setLocation(left_syste,top_syste);
	grp_c=getGraphics();
	if(isyst!=1){
	    open_close_reopen(st_comm[3],true,false,false);
	    open_close_reopen(st_comm[2],true,false,false);
	}	
	System.out.println("avant CCC,i_demarre"+i_demarre);
	if(i_demarre==3||i_demarre==5||i_demarre==8||i_demarre==9)
	    nb_de_gaz=1;
	else
	    nb_de_gaz=2;
	bandeau_x=300;bandeau_y=bottom_temperature;
	if(nb_de_gaz==2){
	    bandeau_x=10;
	    bandeau_y=208;
	}
	Date right_noww=new Date();
	temps_action=appelant.temps_en_secondes(right_noww);
	if(nb_de_gaz==1){
	    point ccc=new point((float)400.,(float)370.);
	    if(isyst==1)
		ccc.assigne((float)170.,(float)320.);
	    if(i_demarre==9){
		if(isyst==0){
		    ccc.y=(float)350.;
		    gas[0]=new gaz(true,ccc,(float)460.,(float)0.,0,this);
		    open_close_reopen(st_graf[5],true,false,false);	
		}else{
		    gas[0]=new gaz(true,ccc,(float)300.,(float)0.,0,this);
		    open_close_reopen(st_spectre[4],true,false,false);
		}
	    }else
		gas[0]=new gaz(true,ccc,(float)400.,(float)0.,0,this);
	}
	if(nb_de_gaz==2){
	    point ccc=new point((float)170.,(float)400.);
	    point ccc1=new point((float)470.,(float)400.);
	    //les parametres suivants ne sont pas bidon, car on aura quelquefois besoin de dessiner une paroi en deplacement
	    gas[0]=new gaz(false,ccc,(float)300.,(float)300.,0,this);
	    System.out.println("APRES GAS");
	    gas[1]=new gaz(false,ccc1,(float)300.,(float)300.,1,this);
	    System.out.println("APRES gas2");
	}
	if(isyst==0){
	    open_close_reopen(st_spectre[0],true,false,false);
	    if(nb_de_gaz==1)
		open_close_reopen(st_spectre[1],true,false,false);
	    if(i_demarre<=1||i_demarre>=3&&i_demarre<9)
		open_close_reopen(st_graf[1],true,false,false);
	    if(i_demarre>=3&&i_demarre<=5){    
		open_close_reopen(st_graf[0],true,false,false);  
		open_close_reopen(st_graf[4],true,false,false);	
		if(i_demarre==5)
		    open_close_reopen(st_graf[3],true,false,false);
	    }
	    if(i_demarre==8)
		open_close_reopen(st_spectre[3],true,false,false);
	}
	dfvt=new point(point_zer0);
	barre_menus();
	//grp_c_clone[isyst]=getGraphics();
	int ray;
	for(int ir=0;ir<=5;ir++){
	    ray=ir+1;
	    System.out.println(" ir "+ir+" ray "+ray);
	    for(int i=0;i<16;i++){
		crop_part[i][ir]=createImage(2*ray,2*ray);
		//System.out.println("i "+i+" ir "+ir+" ray "+ray+" crop_part[i][ir] "+crop_part[i][ir]);
		gTampon_part[i][ir]=crop_part[i][ir].getGraphics();
		gTampon_part[i][ir].setColor(farbe[i].col);
		gTampon_part[i][ir].fillOval(0,0,2*ray,2*ray);
	    }
	}
	if(i_demarre<=2||i_demarre==4||i_demarre==6||i_demarre==7){
	    crop=createImage((int)gas[0].boqal.longueur0+(int)gas[1].boqal.longueur0+20,(int)gas[0].boqal.hauteur0+20);
	    gTampon=crop.getGraphics();
	    //gTampon.setColor(Color.black);
	    //gTampon.drawRect(,10,(int)gas[0].boqal.longueur0+(int)gas[1].boqal.longueur0,(int)gas[0].boqal.hauteur0);

	    //crop_chaud=createImage((int)gas[0].boqal.longueur0+(int)gas[1].boqal.longueur0+20,(int)gas[0].boqal.hauteur0+20);
	    //gTampon_chaud=crop_chaud.getGraphics();

	}else{
	    int rayray=gas[0].boqal.rayon0;
	    crop=createImage(2*rayray+20,2*rayray+20);
	    gTampon=crop.getGraphics();
	    crop0=createImage(2*rayray+20,2*rayray+20);
	    gTampon0=crop0.getGraphics();
	    gTampon0.setColor(Color.black);
	    gTampon0.drawOval(0,0,2*rayray,2*rayray);

	    crop_chaud=createImage(2*rayray+20,2*rayray+20);
	    gTampon_chaud=crop_chaud.getGraphics();
	    crop_chaud0=createImage(2*rayray+20,2*rayray+20);
	    gTampon_chaud0=crop_chaud0.getGraphics();
	    gTampon_chaud0.setColor(Color.red);
	    gTampon_chaud0.drawOval(0,0,2*rayray,2*rayray);
	}
	int nb_de_gaz_m1=nb_de_gaz-1;
	MediaTracker tracker=new MediaTracker(this);
	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_aide_initiale_"+nb_de_gaz_m1+".jpg";
	image_explications_noires=createImage(450,150);
	Graphics gTTampon=image_explications_noires.getGraphics();
	image_explications_noires=Toolkit.getDefaultToolkit().getImage(name);
	tracker.addImage(image_explications_noires,0); 

	name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_aide_initiale_"+nb_de_gaz_m1+"_idemar_"+i_demarre+".jpg";
	appelant.gr.drawString(name,460,90);
	image_explications_rouges=createImage(450,150);
	gTTampon=image_explications_rouges.getGraphics();
	image_explications_rouges=Toolkit.getDefaultToolkit().getImage(name);
	tracker.addImage(image_explications_rouges,1);

	try {tracker.waitForAll(); }
	catch (InterruptedException e) {
	    System.out.println(" image pas arrivee?");
	}
	gTTampon.dispose();
	gTTampon=null;
	if(isyst==0){
	    appel_al_aide(-1);
	    appel_al_aide(-2);
	}
 

	if(gas[0].n_particules>512)
	    nb_evts_sans_calculs=20/(gas[0].n_particules/512);
	pendant_construction_syst_ou_gaz=false;
    }
	float[] calcule_proba_maxwell(float en_par_part){
	    float den_par_ecart=en_par_part/100;
	    proba_maxwell[100]=0;n_theor_somme=0;int k=0;float dn=0;
	    float en=-den_par_ecart/2;
	    for (int j=0;j<800;j++){
		en += den_par_ecart;
		//dn +=n_particules*(float)Math.exp(-en/en_par_part)*den_par_ecart/en_par_part;
		dn +=(float)Math.exp(-en/en_par_part)/100;
		if((j+1)/4*4==j+1){
		    if(k<100)
			proba_maxwell[k]=dn;
		    else
			proba_maxwell[k]+=dn;
		    n_theor_somme +=dn;
		    dn=0;
		    if(k<100)
			k++;
		}
	    }
	    return proba_maxwell;
	}
    void appel_al_aide(int i_tr){
	if(i_tr>=0)
	    dernier_i_tr=i_tr;
	if(i_tr==-1){
	    if(explications_bitte==null)
		open_close_reopen(st_comm[2],true,false,false);
	    explications_bitte.ecrit_aide(0);
	}else{ 
	    if(a_l_aide_svp==null)
		open_close_reopen(st_comm[3],true,false,false);
	    a_l_aide_svp.ecrit_aide(i_tr);
	    if(isyst==0){
		System.out.println(" $$$$$$$$$$$$$$$$$$$$$i_tr "+i_tr);
	    }
	}
    }
    public void run(){
	//if(vient_de_cliquer)
	//System.out.println(" run()pressee "+pressee+" cliquee "+cliquee+" relachee "+relachee);
	num_graphe_a_virer=-1;
	num_spectre_a_virer=-1;
	num_param_comm_a_virer=-1;

	if(pressee){
	    //while(!cliquee){
	    while(!relachee&&!cliquee){
		System.out.println("entree run() ");
	    }
	}
	if(paroi_mouvante){
	    d_momx_part_event=0;
	    d_energie_x_part_event=0;
	    energie_x_part_avant=0;
	    energie_x_part_avant=vit_paroi*vit_paroi/2*masse_paroi_mouvante;
	    cici=energie_x_part_avant;
	    energie_x_part_event=0;
	    impulsion_paroi_mobile_avant=impulsion_paroi_mobile;
	    vit_paroi_avant=vit_paroi;
	    d_pos_paroi_avant=d_pos_paroi;
	    travail_frottement_avant=travail_frottement;
	    d_pos_paroi_event=vit_paroi*d_temps;
	}else{
	    vit_paroi=0;
	    d_pos_paroi_event=0;
	}
	if(gas[0].n_particules>1024)
	    d_temps=(float)0.04;
	else
	    d_temps=(float)0.02;
	if(appelant.systt!=null){
	    //System.out.println(" dans run ");	
	    if(machina_vapore){
		if(!cliquee){
		    if(num_chaud==0&&gas[0].boqal.x_droite>gas[0].boqal.longueur/2*(float)1.5||num_chaud==1&&gas[1].boqal.x_gauche<-gas[1].boqal.longueur/2*(float)1.5)
			num_chaud=inverse_vapeur();
		}else{
		    //machina_vapore=false;
		    command="Revenir a la page principale";
		}
	    }
	    //System.out.println("(paroi_mouvante) "+paroi_mouvante);
	    if(!desactiver){
		if(nb_de_gaz==2){
		    second_passage=false;
		    if(paroi_mouvante){
			cece=vit_paroi*vit_paroi/2*masse_paroi_mouvante;
			for(int i=0;i<nb_de_gaz;i++)
			    gas[i].boqal.d_impulsion_paroi_mobile.assigne(point_zer0);
		    }
		}
		//System.out.println(" dans run 1 false");
		if(isyst==0)
		    for(int i=0;i<nb_de_gaz;i++)
			if(!gas[i].boqal.paroi.reflechissante||paroi_mouvante)
			    gas[i].calcule_rayons_espace_des_p(gas[i].energie_cinetique/gas[i].n_tot_part);
		for(int i=0;i<nb_de_gaz;i++)
		    gas[i].verifications_reinitialisations();
		//System.out.println(" dans run 1 false");	
		for(int i=0;i<nb_de_gaz;i++)
		    gas[i].peint_gaz();
		//System.out.println(" dans run 2 true");	
		for(int i=0;i<nb_de_gaz;i++)
		    gas[i].bouge();
		//System.out.println(" dans run 2 false");	
		for(int i=0;i<nb_de_gaz;i++)
		    gas[i].calcule_et_ecrit_chiffres();
		//System.out.println(" dans run 3 true");	
		if(isyst==0){
		    if(sp_en_cin!=null&&isyst==0)
			sp_en_cin.rafraichit();
		    //System.out.println(" dans run 3 true");	
		    for(int k=0;k<=1;k++)
			if(sp_pos_mom[k]!=null)
			    sp_pos_mom[k].rafraichit();
		    fais_certains_graphes();
		    if(nb_de_gaz==2&&paroi_mouvante){
			for(int i=0;i<nb_de_gaz;i++)
			    gas[i].verif_suppl();
			for(int i=0;i<nb_de_gaz;i++)
			    gas[i].boqal.paroi.dif_impuls_cumulee_avant=gas[i].boqal.paroi.dif_impuls_cumulee;
			gas[0].boqal.x_droite_avant=gas[0].boqal.x_droite;
			gas[1].boqal.x_gauche_avant=gas[1].boqal.x_gauche;
			incremente_les_parametres();
			caca=vit_paroi*vit_paroi/2*masse_paroi_mouvante;
			energie_x_part_event_sec_pass=energie_x_part_event;
			energie_x_part_event+=caca;
			gas[0].nombre_acceptes_second_pass=0;
			gas[1].nombre_acceptes_second_pass=0;
			d_momx_part_event=0;
			d_energie_x_part_event=0;
			vviitt_paroi=vit_paroi;
			vit_paroi_avant=vit_paroi;
			travail_frottement=travail_frottement_avant;
			d_pos_paroi=d_pos_paroi_avant;
			for(int i=0;i<nb_de_gaz;i++)
			    gas[i].boqal.paroi.dif_impuls_cumulee=gas[i].boqal.paroi.dif_impuls_cumulee_avant;
			for(int i=0;i<nb_de_gaz;i++)
			    gas[i].boqal.d_impulsion_paroi_mobile.assigne(point_zer0);
			second_passage=true;
			for(int j=0;j<2;j++)
			    for(int i=0;i<gas[j].nombre_a_revoir;i++){
				if(!gas[j].teilchen[gas[j].numero_a_revoir[i]].reflexion){
				    gas[j].teilchen[gas[j].numero_a_revoir[i]].posit.assigne(gas[j].posit_avant[i]);
				    gas[j].teilchen[gas[j].numero_a_revoir[i]].vit.assigne(gas[j].vit_avant[i]);
				    gas[j].teilchen[gas[j].numero_a_revoir[i]].fais_la_reflexion_xxx();
				    gas[j].teilchen[gas[j].numero_a_revoir[i]].termine_reflechit();
				}
			    }
			if(gas[0].nombre_acceptes_second_pass!=0||gas[1].nombre_acceptes_second_pass!=0){
			    incremente_les_parametres();
			    cece=vit_paroi*vit_paroi/2*masse_paroi_mouvante;
			    energie_x_part_event_sec_pass+=cece;
			}
			for(int i=0;i<nb_de_gaz;i++)	
			    gas[i].boqal.surf_perim_instant();
			for(int i=0;i<nb_de_gaz;i++)	
			    gas[i].contre_le_deplacement_paroi();
		    }
		}
		Date right_now=new Date();
		temps_courant=appelant.temps_en_secondes(right_now);
		if(temps_courant-temps_courant_prec>2){
		    temps_courant_prec=temps_courant;
		    System.out.println("n_event "+n_event+" travaux_elementaires "+gas[0].travaux_elementaires);
		}
		if(relachee){
		    if(!machina_vapore)
			cliquee=false;
		    pressee=false;
		}
		n_event++;
		if(n_event/50*50==n_event){
		    if(a_l_aide_svp!=null)
			if(dernier_i_tr<0){
			    a_l_aide_svp.ecrit_aide(-1);
			    a_l_aide_svp.ecrit_aide(-2);
			}else
			    a_l_aide_svp.ecrit_aide(dernier_i_tr);
		    if(n_parametre_a_voir!=-1&&voir_parametre<=3){
			if(n_parametre_a_voir!=3){
			    if(paramt!=null)
				paramt.ecrit_aide(n_parametre_a_voir);
			}else
			    if(paramt_entropie!=null)
				paramt_entropie.ecrit_aide(3);
			voir_parametre++;
		    }
		}
		n_event_calculs++;
		if(n_event_calculs==nb_evts_sans_calculs)
  		    n_event_calculs=0;
	    }	    
	    //System.out.println(" dans run 3 false");	
	}
    }
    void fais_certains_graphes(){
	if(n_event<=99&&(n_event+1)/5*5==n_event+1)
	    for(int i=0;i<5;i++){
		if(quel_graf[i]!=null){
		    quel_graf[i].grap[i].setColor(Color.blue);
		    quel_graf[i].grap[i].drawString(" Un moment svp ",50,150);
		    if(n_event==99)
			appelant.eraserect(quel_graf[i].grap[i],0,0,500,500,Color.white);
		}
	    }
	if(n_event_calculs==nb_evts_sans_calculs-1&&n_event>=100){
	    if(graf_pression!=null){
		quel_graphe=graf_pression;
		mariotte0=gas[0].n_tot_part*gas[0].temperature/gas[0].boqal.surface;
		if(nb_de_gaz==2){
		    mariotte1=gas[1].n_tot_part*gas[1].temperature/gas[1].boqal.surface;
		    quel_graphe.demarre_dessine_graphe(Math.max(mariotte0,mariotte1),gas[0].pression,gas[1].pression,3);		    
		}else{
		    quel_graphe.demarre_dessine_graphe(mariotte0,gas[0].pression,mariotte0,2);
		}
	    }
	    if(grp_en_tot!=null&&!((i_demarre==6||i_demarre==7||i_demarre==8)&&gas[0].boqal.paroi.reflechissante)){
		quel_graphe=grp_en_tot;
		if(nb_de_gaz==2){
		    if(gas[0]!=null&&gas[1]!=null){
			st1_energie="Totale";
			st2_energie="gaz 1";
			st3_energie="gaz 0";
			quel_graphe.demarre_dessine_graphe((gas[0].energie_totale+gas[1].energie_totale),gas[0].energie_totale,gas[1].energie_totale,3);
		    }
		}
	    }
	}
    }
    void incremente_les_parametres(){
	d_imp_paroi_totale=0;
	for(int i=0;i<2;i++)
	    d_imp_paroi_totale += gas[i].boqal.d_impulsion_paroi_mobile.x;
	impulsion_paroi_mobile += (d_imp_paroi_totale-k_paroi*vit_paroi*d_temps);
	vit_paroi=impulsion_paroi_mobile/masse_paroi_mouvante;
	coco=masse_paroi_mouvante*(vit_paroi*vit_paroi-vit_paroi_avant*vit_paroi_avant)/2;
	d_pos_paroi_event=vit_paroi*d_temps;
	travail_frottement += k_paroi*vit_paroi*d_pos_paroi_event;
	//System.out.println("$$$travail_frottement "+travail_frottement+" vit_paroi "+vit_paroi+" d_pos_paroi "+d_pos_paroi);
	d_pos_paroi +=d_pos_paroi_event ;
	gas[0].boqal.x_droite += d_pos_paroi_event;
	gas[1].boqal.x_gauche += d_pos_paroi_event;
	//System.out.println(" vit_paroi "+vit_paroi+" vit_paroi_avant "+vit_paroi_avant+" d_momx_part_event "+d_momx_part_event+" d_energie_x_part_event "+d_energie_x_part_event+" coco "+coco+" d_pos_paroi_event "+d_pos_paroi_event);
    }
    
    public void actionPerformed(ActionEvent e){
	command=e.getActionCommand();
	System.out.println("ééééééééé command "+command);
	if(command!=""){
	    //grp_c.setColor(Color.red);
	    //grp_c.drawString("command "+command,20,200);
	    fin_gerelesmenus_avec_souris=false;
	    Date right_noww=new Date();
	    temps_action=appelant.temps_en_secondes(right_noww);
	    appelant.temps_initial_en_secondes=temps_action;
	    System.out.println(" command "+command+" command_prec "+command_prec+" temps_action "+temps_action+" temps_action_prec "+temps_action_prec);
	    if(command==command_prec&&temps_action<temps_action_prec+4){
		System.out.println(" command "+command+" command_prec "+command_prec);
		command="";
	    }else{
		appelant.eraserect(grp_c,bottom_syste-50,0,bottom_syste,right_syste,Color.white);
		if(command=="info calcul entropie"){
		    n_parametre_a_voir=3;
		    voir_parametre=0;
		    if(paramt_entropie==null)
			open_close_reopen(st_comm[1],true,false,false);
		    System.out.println(" ppppppppppn_parametre_a_voir "+n_parametre_a_voir+" paramt_entropie "+paramt_entropie);
		    command="";
		}
		if (command=="stopper ou reprendre"){
		    desactiver=!desactiver;
		    command_prec=command;
		    command="";
		}else if ((command=="Revenir a la page principale")||(command=="Sortir du programme"||command=="Revenir a la page initiale avec infos")){
		    command_prec=command;
		    //command="";
		    le_virer=true;
		}
	    }
	}
    }

    int inverse_vapeur(){
	int n=1-num_chaud;
	gas[n].en_par_particule0=3000;
	gas[n].n_particules=1024;
	gas[n].n_tot_part=1024;
	gas[1-n].en_par_particule0=1000;
	gas[1-n].n_particules=512;
	gas[1-n].n_tot_part=512;
	for (int i=0;i<nb_de_gaz;i++)
	    gas[i].cree_particules();
	return n;
    }
    void machine_a_vapeur(){
	machina_vapore=true;
	System.out.println(" machina_vapore "+machina_vapore);
	st_bandeau=" Pour terminer, cliquez dans cette fenetre ";
	elimination_reouverture_fenetres(false);
	for (int i=0;i<nb_de_gaz;i++)
	    for (int j=0;j<33;j++)
		gas[i].incapaciter[j]=true;
	num_chaud=1;
	gas[0].boqal.x_droite=gas[0].boqal.longueur/2;
	gas[1].boqal.x_gauche=-gas[1].boqal.longueur/2;
	num_chaud=inverse_vapeur();
	cliquee=false;
    }
    void barre_menus(){
	MenuBar menu_syst=new MenuBar();
	Menu graph= new Menu("Graphes");
	if(i_demarre==3||i_demarre==8||paroi_mouvante||nb_de_gaz==1&&!gas[0].boqal.paroi.reflechissante)
	    gas[0].remplis_ruf(20,false,graph);
	Menu dS_sur_dQ_sur_Te=new Menu("dS/(dQ/T)");
	MenuItem n_23= new MenuItem(str_menus0[23]);
	dS_sur_dQ_sur_Te.add(n_23);
	n_23.addActionListener(ruf);
	MenuItem cal_entrop=new MenuItem("info calcul entropie");
	cal_entrop.addActionListener(ruf);
	dS_sur_dQ_sur_Te.add(cal_entrop);
	graph.add(dS_sur_dQ_sur_Te);
	dS_sur_dQ_sur_Te.setEnabled(!(isyst==1||(gas[0].boqal.paroi.reflechissante||gas[0].osc_harm_limite||particules_composees&&!gas[0].boqal.paroi.reflechissante||i_demarre==9)));
	gas[0].remplis_ruf(25,false,graph);	
	gas[0].incapaciter[17]=gas[0].boqal.paroi.reflechissante;
	if(paroi_mouvante)
	    gas[0].incapaciter[17]=false;
	if(nb_de_gaz==2)
	    gas[1].incapaciter[17]=gas[0].incapaciter[17];
	Menu entropp=new Menu("entropie");
	if(!un_des_2_osc_harm)
	    gas[0].remplis_ruf(17,false,entropp);
	entropp.add(cal_entrop);
	graph.add(entropp);
	menu_syst.add(graph);

	Menu spectres= new Menu("spectres");
	toto_int=0;
	for(int j=0;j<nb_de_gaz;j++)
	    if(gas[j].geschwind!=null)
		toto_int++;
	if(toto_int==0)
	    gas[0].remplis_ruf(21,false,spectres);
	if(sp_en_cin==null)
	    gas[0].remplis_ruf(22,false,spectres);
	if(particules_composees)
	    gas[0].remplis_ruf(24,false,spectres);
	Menu sp_meme_surf= new Menu(st_spectre[1]);
	toto_int=0;
	if(sp_pos_mom[0]==null){
	    gas[0].remplis_ruf(26,false,sp_meme_surf);
	    toto_int++;
	}
	totologic=gas[0].avec_spin;
	if(nb_de_gaz==2)
	    totologic=totologic||gas[1].avec_spin;
	if(!totologic&&sp_pos_mom[1]==null){
	    gas[0].remplis_ruf(27,false,sp_meme_surf);
	    toto_int++;
	}
	if(toto_int!=0)
	    spectres.add(sp_meme_surf);
	menu_syst.add(spectres);
	
	Menu revenir= new Menu("Sortir,stopper");
	MenuItem iteb1a=new MenuItem("stopper ou reprendre");
	if(isyst==1)
	    iteb1a.setEnabled(false);
	revenir.add(iteb1a);
	iteb1a.addActionListener(this);
	MenuItem iteb1=new MenuItem("Revenir a la page principale");
	if(isyst==1)
	    iteb1.setEnabled(false);
	revenir.add(iteb1);
	iteb1.addActionListener(this);
	MenuItem iteb1c=new MenuItem("Revenir a la page initiale avec infos");
	if(isyst==1)
	    iteb1c.setEnabled(false);
	revenir.add(iteb1c);
	iteb1c.addActionListener(this);
	MenuItem iteb12=new MenuItem("Sortir du programme");
	if(isyst==1)
	    iteb12.setEnabled(false);
	revenir.add(iteb12);iteb12.addActionListener(this);
	menu_syst.add(revenir);
	for (int i=0;i<nb_de_gaz;i++){
	    System.out.println(" nb_de_gaz "+nb_de_gaz+" i "+i);
	    gas[i].menu_bar(menu_syst);
	}
	barre_menus_faite=true;	
	setMenuBar(menu_syst);
	
    }
    void reinitialsations_dans_systeme(int n_gaz){
	System.out.println("debut reinitialsations_dans_systeme");
	for (int k=0;k<7;k++)
	    n_moyenne[k]=0;
	d_pos_paroi=0;
	impulsion_paroi_mobile=0;vit_paroi=0;
        if(paroi_mouvante)
	    d_imp_paroi_totale=0;
	n_event=0;n_event_calculs=0;
    }
    
    boolean est_ce_possible(int n_p_comp,int n_p_f, int rey_f, int n_g){
	//System.out.println(" n_g "+n_g);
	pas_possible=" ";
	if(n_p_f<4)
	   pas_possible=" Impossible a realiser, votre nombre de particules finales est inferieur a 4";
	if(n_p_f>2048)
	   pas_possible=" Impossible a realiser, votre nombre de particules finales est superieur a 2048";
	if(rey_f>6)
	   pas_possible=" Impossible a realiser, le rayon de vos particules serait superieur a 6";
	if(rey_f<1)
	   pas_possible=" Impossible a realiser, le rayon de vos particules serait inferieur a 1";
	if(!gas[n_g].interaction_nulle){
	    float Su=0;
	    if(i_demarre==6)
		Su=pi*n_p_f/2*(1+(float)0.25)*rey_f*rey_f;                                              
	    else
		Su=pi*(n_p_f*rey_f*rey_f+n_p_comp*(float)Math.pow(rayon_grosse_particule,2));
	    if(Su>0.5*gas[n_g].boqal.surface){
		pas_possible=" Impossible a realiser, la surface totale de vos particules est trop grande";
		//System.out.println("Su "+Su+" gas[n_g].boqal.surface "+gas[n_g].boqal.surface);
	    }
	}
	if(pas_possible!=" "){
	    grp_c.setColor(Color.red);
	    appelant.eraserect(grp_c,bottom_syste-50,0,bottom_syste,right_syste,Color.white);
	    grp_c.drawString(pas_possible,10,bottom_syste-30);
	    grp_c.setColor(Color.black);
	    return false;
	}else
	//System.out.println("c'est possible!");
	    return true;
    }
    public void met_les_titres(){
	appelant.eraserect(grp_c, 0, 0, bottom_n_part, 1000,Color.white);
	grp_c.setColor(Color.black);
	if(!un_des_2_osc_harm)
	    grp_c.drawString("energie,temperature",10,bottom_temperature);
	else
	    grp_c.drawString("Ecin-Tr,temperature",10,bottom_temperature);
	grp_c.drawLine(10,bottom_temperature,right_temperature,bottom_temperature);
	grp_c.drawString("'pression' P",10,bottom_pression);
	grp_c.drawLine(10,bottom_pression,right_pression,bottom_pression);
	grp_c.drawString("nT/P(surface) ",10,bottom_nTsp);
	grp_c.drawLine(10,bottom_nTsp,right_nTsp,bottom_nTsp);
	grp_c.drawString("libre parcours moyen(pixels)",10,bottom_libre_p);
	grp_c.drawLine(10,bottom_libre_p,right_libre_p,bottom_libre_p);
	grp_c.drawString("nb de particules ",10,bottom_n_part);
	grp_c.setColor(Color.red);
	if(nb_de_gaz==2)
	    grp_c.setFont(appelant.times_gras_24);
	grp_c.drawString(st_bandeau,bandeau_x,bandeau_y);
	//if(machina_vapore)
	grp_c.setFont(appelant.times_gras_14);
	grp_c.setColor(Color.black);
	totologic=gas[0].avec_spin;
	if(nb_de_gaz==2)
	    totologic=totologic||gas[1].avec_spin;
	if(totologic){
	    grp_c.drawString("total des spins ",10,bottom_spins);
	    grp_c.drawLine(10,bottom_spins,right_spins,bottom_spins);
	}
    }
    abstract class graphes_spectres extends Frame{
	int numg=-1;
	public graphes_spectres(String s,int numg1){
	    super(s);
	    numg=numg1;
	}
	abstract void abandon();
    }
    abstract class graphes extends graphes_spectres{
	static final int top0=50,left0=710,bottom0 = 300,right0 = 1110;
	int top,left,bottom,right;int t_ge=0,t_gg=0;
	int hauteur_totale=0,ordonnee_min,ordonnee_max;
	boolean occupe=false;
	point elonga_maxp_minp;
	Graphics grap[]=new Graphics[8];int num_graphe;int topp;float eloo;
	public graphes(String s,int numg1){
	    super(s,numg1);
	    num_graphe=numg;
	    if(numg!=7){
		top=top0+numg*40;left=left0+numg*20;
		bottom=bottom0+numg*40;right=right0+numg*20;
	    }else{
		top=140;left=540;
		bottom=440;right=940;
	    }
	    elonga_maxp_minp=new point(point_zer0);
	    ordonnee_max=40;
	    ordonnee_min=bottom-top-40;
	    hauteur_totale=ordonnee_min-ordonnee_max;
	    pack();
	    setSize(right-left,bottom-top);
	    topp=top;
	    setLocation(left,topp);
	}
	void stocke_et_dessine(float[] elongation, point elongg_maxp_minp, point elongg_max_min,Color col,int i ,boolean dernier,float elooo){
	    if(!dernier){
		if(elongg_max_min.x>elongg_maxp_minp.x||elongg_max_min.y<elongg_maxp_minp.y){
		    eloo=-(elongation[i]-ordonnee_min)*(elongg_maxp_minp.x-elongg_maxp_minp.y)/hauteur_totale+elongg_maxp_minp.y;
		    elongation[i]=ordonnee_min-(eloo-elongg_max_min.y)*hauteur_totale/(elongg_max_min.x-elongg_max_min.y);
		    if(grap[num_graphe]!=null){
			grap[num_graphe].setColor(Color.black);
			grap[num_graphe].drawLine(0,ordonnee_min,400,ordonnee_min);
		    }
		}
	    }else
		elongation[i]=ordonnee_min-(elooo-elongg_max_min.y)*hauteur_totale/(elongg_max_min.x-elongg_max_min.y);
	    grap[num_graphe].setColor(col);
	    for (int j=-1;j<=1;j++)
		if(grap[num_graphe]!=null)
		    grap[num_graphe].drawLine(i-1+10,(int)elongation[i]+j,i+1+10,(int)elongation[i]+j);
	}
	void dessine_point(float elo,point elongg_max,Color col,int i){
	    eloo=bottom-top-40-(elo*(bottom-top-80)/elongg_max.x);
	    if(grap[num_graphe]!=null)
		grap[num_graphe].setColor(col);
	    for (int j=-1;j<=1;j++)
		if(grap[num_graphe]!=null)
		    grap[num_graphe].drawLine(i-1+20,(int)eloo+j,i+1+20,(int)eloo+j);
	}
	point vas_y_dessine(int ipl, float[][] elonga,point el_max_min,int temps_graphe,float elooo){
	    point elonga_max_min=new point(el_max_min);
	    //System.out.println("iiiiiiiiiiiiiiiiiiiiiiipl "+ipl);
	    Color col=Color.blue;//ipl==0
	    if(ipl==1)
		col=Color.black;
	    if(ipl==2)
		col=Color.red;
	    if(ipl==4)
		col=Color.white;//seulement pour fixer le maximum
	    if(ipl==3)
		col=Color.green;
	    if(quel_graphe==grp_dS_sur_dQ_sur_T)
		elonga_maxp_minp.assigne(el_max_min);
	    else{
		if(ipl==4){
		    elonga_maxp_minp.assigne(elonga_max_min);
		    if(elooo>elonga_max_min.x)
			elonga_max_min.x=elooo;
		    if(quel_graphe==graf_entropie&&elooo<elonga_max_min.y)
			elonga_max_min.y=elooo;
		}
	    }
	    if(ipl<=3){
		for(int i=0;i<=temps_graphe;i++){
		    if(ipl<=1){
			stocke_et_dessine(elonga[ipl],elonga_maxp_minp,elonga_max_min,col,i,i==temps_graphe,elooo);
			if(quel_graphe!=grp_dS_sur_dQ_sur_T&&ipl<=1&&i==temps_graphe){
			    if(Math.abs(elooo)>100)
				totoString=""+(int)elooo;
			    else{
				cici=100;cucu=1;
				for (int j=0;j<8;j++){
				    cici/=10;cucu*=10;
				    if(Math.abs(elooo)>cici){
					if(elooo>0)
					    totoString=""+(float)Math.round(elooo*cucu)/cucu;
					else
					    totoString="-"+(float)Math.round(elooo*cucu)/cucu;
					break;
				    }
				}
			    }
			    if(grap[num_graphe]!=null)
				grap[num_graphe].drawString(totoString,i+3+20,(int)elonga[ipl][i]); 
			    //System.out.println(" elooo "+elooo+" totoString "+totoString); 
			}			    
		    }else{
			if(quel_graphe==grp_dS_sur_dQ_sur_T)
			    if(grp_dS_sur_dQ_sur_T.de_quel_item=="0.95")
				dessine_point((float).05,elonga_maxp_minp,Color.red,i);
			    else
				dessine_point((float).00005,elonga_maxp_minp,Color.red,i);
			else{
			    if(ipl==2)
				stocke_et_dessine(elonga[2],elonga_maxp_minp,elonga_max_min,Color.red,i,i==temps_graphe,elooo);
			    else if(ipl==3)
				if(quel_graphe==graf_pression)
				    stocke_et_dessine(elonga[3],elonga_maxp_minp,elonga_max_min,Color.green,i,i==temps_graphe,elooo);
			}
		    }
		}
	    }
	    return elonga_max_min;
	}
	void demarre_dessine_graphe (float arg_max,float arg_0,float arg_1,int nb_arguments){
	    if(quel_graphe==graf_pression)
		quel_graphe.dessine_graphe(arg_max*(float)2.5,4);//couleur blanche pour fixer le maximun dans le plot
	    else if(quel_graphe==graf_entropie){
		quel_graphe.dessine_graphe(arg_0,4);
		if(Math.abs(arg_1+1)>0.001)
		    quel_graphe.dessine_graphe(arg_1,4);
	    }else if(quel_graphe!=grp_dS_sur_dQ_sur_T)
		quel_graphe.dessine_graphe(arg_max,4);
	    if(quel_graphe.grap[num_graphe]!=null)
		appelant.eraserect(quel_graphe.grap[num_graphe],0,0,bottom-top,right-left, Color.white);
	    quel_graphe.designe_couleurs();
	    if(nb_de_gaz==1||(i_demarre==6||i_demarre==7||i_demarre==4||i_demarre==8||paroi_mouvante)&&quel_graphe==grp_en_tot){
		if(nb_arguments==3){
		    quel_graphe.dessine_graphe(arg_max,2);
		    quel_graphe.dessine_graphe(arg_1,1);
		    quel_graphe.dessine_graphe(arg_0,0);
		}
		if(nb_arguments==2){
		    if(quel_graphe!=grp_en_tot){
			quel_graphe.dessine_graphe(arg_0,1);
			quel_graphe.dessine_graphe(arg_1,0);		
		    }else{
			quel_graphe.dessine_graphe(arg_0,2);
			quel_graphe.dessine_graphe(arg_1,0);		
		    }
		}
		if(nb_arguments==1)
		    quel_graphe.dessine_graphe(arg_0,0);
	    }else{
		if(nb_arguments==3){
		    if(quel_graphe==graf_pression||quel_graphe==graf_entropie||quel_graphe==grp_en_tot){
			if(quel_graphe==graf_pression)
			    quel_graphe.dessine_graphe(mariotte0,2);
			else if(quel_graphe==graf_entropie)
			    quel_graphe.dessine_graphe(arg_0+arg_1,2);
			else
			    quel_graphe.dessine_graphe(arg_1,2);
		    }else 
			quel_graphe.dessine_graphe(arg_max*2,2);
		    quel_graphe.dessine_graphe(arg_0,1);
		    quel_graphe.dessine_graphe(arg_1,0);
		}else if(nb_arguments==2){
		    //System.out.println(" arg_max "+arg_max+"  arg_0 "+ arg_0+"  arg_1 "+ arg_1);
		    quel_graphe.dessine_graphe(arg_0,1);
		    quel_graphe.dessine_graphe(arg_1,0);
		}
		if(quel_graphe==graf_pression)
		    quel_graphe.dessine_graphe(mariotte1*(float)1.02,3);//le +1.02 en cas d'égalité avec le rouge
	    }
	}
	int fin_dessine_graphe(int t_g,int quelle_couleur){
	    t_ge=t_g;
	    if(quelle_couleur==0){
		t_ge++;
	    }
	    if (t_ge == right-left){
		t_ge = 0;
		appelant.eraserect(grap[num_graphe],0,0,bottom-top,right-left,Color.white);
	    }
	    return t_ge;
	}
	void designe_couleurs_2(int ng){
	    if(grap[ng]!=null){
		grap[ng].setColor(Color.black);
		grap[ng].drawString("gaz 0",right-left-80,bottom-top -80);
		if(nb_de_gaz==2){
		    grap[ng].setColor(Color.blue);
		    grap[ng].drawString("gaz 1",right-left-80,bottom-top -40);
		}
	    }
	}
	abstract void dessine_graphe (float energ,int iiij);
	abstract void designe_couleurs();
    }
    class graphe_energies extends graphes{
	float elong[][]=new float [3][401];boolean dessine_press=false;
	int n_pts_graphe=0,temps_graphe=0;
	point elong_max_min;
	boolean occupe=false;
	public graphe_energies(String s,int numg1){
	    super(s,numg1);
	    System.out.println("cree graphe_energies "+s+" num_graphe "+num_graphe);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			gas[0].incapaciter[20]=false;
			num_graphe_a_virer=numg;
		    };
		});
	    elong_max_min=new point(point_zer0);
	    grap[numg]= getGraphics(); 
	    grap[numg].setColor(Color.blue);
	    setVisible(true);
	}
	void abandon(){
	    grp_en_tot.dispose();
	    grp_en_tot=null;
	    quel_graf[numg]=null;
	}
	void dessine_graphe(float arg, int quelle_couleur){
	    occupe=true;
	    //System.out.println("dans dessine_graphe   arg "+ arg+" quelle_couleur "+quelle_couleur);
	    dessine_press=false;
	    point el_max=new point(elong_max_min);
	    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,arg);
	    t_gg=temps_graphe;
	    temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
	    occupe=false;
	}
	void designe_couleurs(){
	    occupe=true;
	    if(grap[num_graphe]!=null){
		grap[num_graphe].setColor(Color.red);
		//System.out.println("designe_couleurs");
		grap[num_graphe].drawString(st1_energie,right-left-130,bottom-topp-100);
	    }
	    if(grap[num_graphe]!=null){
		grap[num_graphe].setColor(Color.black);
		if(st2_energie=="Prise a la paroi")
		    grap[num_graphe].setColor(Color.blue);
		grap[num_graphe].drawString(st2_energie,right-left-130,bottom-topp-80);
		grap[num_graphe].setColor(Color.blue);
		grap[num_graphe].drawString(st3_energie,right-left-130,bottom-topp-60);
	    }
	    occupe=false;
	}
    }
    void open_close_reopen(String st_open,boolean open,boolean close,boolean reopen){
	for(int i=0;i<8;i++){
	    if(st_open==st_graf[i]){
		totologic=quel_graf[i]!=null;
		if((close||reopen)&&totologic){
		    quel_graf[i].grap[i]=null;
		    quel_graf[i].abandon();
		    System.out.println(" fermeture graphe i "+i);
		}
		if(i<6)
		    if(open||reopen&&totologic){
			if(quel_graf[i]==null){
			    if(i==0){
				grp_en_tot=new graphe_energies(st_graf[i],i);
				quel_graf[i]=grp_en_tot;
			    }else if(i==1){
				graf_pression=new graphe_pression(st_graf[i],i);
				quel_graf[i]=graf_pression;
			    }else if(i==3){
				grp_dS_sur_dQ_sur_T=new graphe_dS_sur_dQ_sur_T(st_graf[i],i);
				quel_graf[i]=grp_dS_sur_dQ_sur_T;
			    }else if(i==4){
				graf_entropie=new graphe_entropie(st_graf[i],i);
				quel_graf[i]=graf_entropie;
			    }else if(i==5){
				dist_carr_moy=new graphe_distance_carree_moyenne(st_graf[i],i); 
				quel_graf[i]=dist_carr_moy;
			    }
			    
			    System.out.println(" (re)ouverture graphe i "+i);
			    return;	    
			}
		    }
	    }
	}
	for(int i=4;i>=0;i--){
	    if(st_open==st_spectre[i]){
		totologic=quel_spectre[i]!=null;
		if((close||reopen)&&totologic)
		    if(i!=4){
			quel_spectre[i].abandon();
		    }else
			for(int j=0;j<nb_de_gaz;j++){
			    if(quel_spectre[i+j]!=null)
				quel_spectre[i+j].abandon();
			}
		if(open||reopen&&totologic){
		    if(quel_spectre[i]==null){
			if(i==0){
			    sp_en_cin=new spectre_energies(st_spectre[i],i);
			    quel_spectre[i]=sp_en_cin;
			    quel_graf[6]=sp_en_cin.grp_equilibre;
			    
			}else if(i==1||i==2){
			    sp_pos_mom[i-1]=new spectre_positions_moments(st_spectre[i],i-1);
			    quel_spectre[i]=sp_pos_mom[i-1];
			}else if(i==3){
			    sp_nb=new spectre_nombre_de_particules_composees(st_spectre[i],i);
			    quel_spectre[i]=sp_nb;
			    quel_graf[7]=sp_nb.grp_concentration;
			}else if(i==4){
			    for(int j=0;j<nb_de_gaz;j++){
				if(gas[j].geschwind==null){
				       gas[j].geschwind=gas[j].open_represente(st_spectre[i+j],i+j);
				       quel_spectre[i+j]=gas[j].geschwind;
				   }
			    }
			}
			return;		    		    
		    }
		}
	    }
	}
	for(int i=3;i>=0;i--){
	    if(st_open==st_comm[i]){
		totologic=quel_comm[i]!=null;
		if((close||reopen)&&totologic)
		    quel_comm[i].abandon();
		if(open||reopen&&totologic){
		    if(quel_comm[i]==null){
			if(i==0){
			    paramt=new parametre(st_comm[i],i);
			    quel_comm[i]=paramt;
			}else if(i==1){
			    paramt_entropie=new parametre_entropie(st_comm[i],i);
			    quel_comm[i]=paramt_entropie;
			}else if(i==2){
			    explications_bitte=new explications(st_comm[i],i);
			    quel_comm[i]=explications_bitte;
			    explications_bitte.ecrit_aide(0);
			}else if(i==3){
			    a_l_aide_svp=new aider(st_comm[i],i);
			    quel_comm[i]=a_l_aide_svp;
			}
			System.out.println(" reouverture spectre i "+i);
			return;		    		    
		    }
		}
	    }
	}
    }
    class graphe_distance_carree_moyenne extends graphes{
	float elong[][]=new float [3][401];boolean dessine_press=false;
	int n_pts_graphe=0,temps_graphe=0;
	point elong_max_min;
	boolean occupe=false;
	public graphe_distance_carree_moyenne(String s,int numg1){
	    super(s,numg1);
	    System.out.println("cree graphe "+s+" num_graphe "+num_graphe);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			System.out.println("dist_carr_moy out  ");
			num_graphe_a_virer=numg;
		    };
		});
	    elong_max_min=new point(point_zer0);
	    grap[numg]= getGraphics(); 
	    grap[numg].setColor(Color.blue);
	    setVisible(true);
	}
	void abandon(){
	    dist_carr_moy.dispose();
	    dist_carr_moy=null;
	    quel_graf[numg]=null;
 	}
	void dessine_graphe(float press, int quelle_couleur){
	    occupe=true;
	    float elo=press;
	    //System.out.println("dans dessine_graphe   press "+ press+" quelle_couleur "+quelle_couleur);
	    dessine_press=false;
	    point el_max=new point(elong_max_min);
	    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,elo);

	    //System.out.println(" elo "+elo+"elong_max "+elong_max+" temps_graphe "+temps_graphe);
	    t_gg=temps_graphe;
	    temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
	    occupe=false;
	}
	void designe_couleurs(){
	    occupe=true;
	    if(grap[num_graphe]!=null){
		grap[num_graphe].setColor(Color.black);
		//System.out.println("designe_couleurs");
		grap[num_graphe].drawString(st1_dist_carr,right-left-100,bottom-topp-100);
		grap[num_graphe].setColor(Color.blue);
		grap[num_graphe].drawString(st2_dist_carr,right-left-100,bottom-topp-80);
	    }
	    occupe=false;
	}
    }
    
    class graphe_entropie extends graphes{
	float elong[][]=new float [3][401];
	int n_pts_graphe=0,temps_graphe=0;
	point elong_max_min;
	boolean occupe=false;
	public graphe_entropie(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			if(paramt!=null){
			    paramt.dispose();
			    paramt=null;
			    n_parametre_a_voir_prec=-1;
			}
			calcul_hypervolu=false;
			num_graphe_a_virer=numg;
		    };
		});		
	    System.out.println("creeg graphe_entropie "+s+" num_graphe "+num_graphe);		//setLocation(left,top);
	    elong_max_min=new point(point_zer0);
	    grap[numg]= getGraphics(); 
	    grap[numg].setColor(Color.blue);
	    setVisible(true);
	    designe_couleurs();
	}
	void abandon(){
	    graf_entropie.dispose();
	    graf_entropie=null;
	    quel_graf[numg]=null;
	}
	void dessine_graphe (float entrop,int quelle_couleur){
	    occupe=true;
	    float elo=entrop;
	    point el_max=new point(elong_max_min);
	    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,elo);

	    t_gg=temps_graphe;
	    temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
	    occupe=false;
	    
	}
	void designe_couleurs(){
	    occupe=true;
	    if(nb_de_gaz==2){
		designe_couleurs_2(num_graphe);
		totologic=false;
		if(gas[0]!=null)
		    totologic=!gas[0].boqal.paroi.reflechissante;
		if(paroi_mouvante||totologic){
		    if(grap[num_graphe]!=null){
			grap[num_graphe].setColor(Color.red);
			grap[num_graphe].drawString("total ",right-left-100,bottom-top -100);
		    }
		}
	    }
	    occupe=false;
	}
    }
    class graphe_dS_sur_dQ_sur_T extends graphes{
	float elong[][]=new float [3][401],elong_h[]=new float [401];
	String de_quel_item;float dS_sur_dQ_div_T=0;
	int n_pts_graphe=0,temps_graphe=0;
	point elong_max_min;
	boolean occupe=false;
	public graphe_dS_sur_dQ_sur_T(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_graphe_a_virer=numg;
		    };
		});		
	    
	    System.out.println("cree graphe_dS_sur_T "+s+" num_graphe "+num_graphe);		//setLocation(left,top);
	    elong_max_min=new point(point_zer0);
	    grap[numg]= getGraphics(); 
	    grap[numg].setColor(Color.blue);
	    setVisible(true);
	    de_quel_item=str_menus0[23];
	    System.out.println("cree graphe_dS_sur_T "+s+" num_graphe "+num_graphe+" de_quel_item "+de_quel_item);
	    if(particules_composees)
		de_quel_item="0.95";
	    if(de_quel_item=="0.95")
		elong_max_min.x=(float)0.1;
	    else
		//elong_max=(float)0.01;
		elong_max_min.x=(float)0.0001;
	    designe_couleurs();
	}
	void abandon(){
	    grp_dS_sur_dQ_sur_T.dispose();
	    grp_dS_sur_dQ_sur_T=null;
	    quel_graf[numg]=null;
	}
	void dessine_graphe (float hyperv,int quelle_couleur){
	    occupe=true;
	    if(de_quel_item=="0.95")
		dS_sur_dQ_div_T=hyperv-(float)0.95;
	    else{
		dS_sur_dQ_div_T=hyperv-(float)0.99995;
		//if(quelle_couleur==0)
		//dS_sur_dQ_div_T+=0.0000015;
		    
	    }
	    point el_max=new point(elong_max_min);
	    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,dS_sur_dQ_div_T);
	    //System.out.println(de_quel_item+" hyperv "+hyperv+ "hyperv "+hyperv+" el_max "+el_max);
	    t_gg=temps_graphe;
	    temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
	    occupe=false;
	}
	void designe_couleurs(){
	    occupe=true;
	    if(grap[num_graphe]!=null){
		grap[num_graphe].setColor(Color.red);
		if(de_quel_item=="0.95"){
		    grap[num_graphe].drawString("0.95",right-left-140,bottom-topp-40);
		    grap[num_graphe].drawString("1.05",right-left-140,40);
		}else{
		    grap[num_graphe].drawString("0.99995",right-left-140,bottom-topp-40);
		    grap[num_graphe].drawString("1.00005",right-left-140,40);
		}
	    }
	    if(grap[num_graphe]!=null){		
		grap[num_graphe].drawString("1.00",right-left-140,(bottom-topp)/2);
		grap[num_graphe].drawLine(right-left-140,(bottom-topp)/2,right-left-140+20,(bottom-topp)/2);
	    }
	    designe_couleurs_2(num_graphe);
	    occupe=false;
	}
    }
    class graphe_pression extends graphes{
	float elong[][]=new float [4][401];boolean dessine_press=false;
	int n_pts_graphe=0,temps_graphe=0;
	point elong_max_min;
	boolean occupe=false;
	public graphe_pression(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_graphe_a_virer=numg;
		    };
		});		
	    System.out.println("creeg graphe_entropie "+s+" num_graphe "+num_graphe);		//setLocation(left,top);
	    elong_max_min=new point(point_zer0);
	    grap[numg]= getGraphics(); 
	    grap[numg].setColor(Color.blue);
	    setVisible(true);
	    //designe_couleurs();
	}
	void abandon(){
	    graf_pression.dispose();
	    graf_pression=null;
	    quel_graf[numg]=null;
	}
	void dessine_graphe (float press,int quelle_couleur){
	    occupe=true;
	    float elo=press;
	    dessine_press=false;
	    point el_max=new point(elong_max_min);
	    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,elo);

	    //System.out.println(" elo "+elo+"elong_max "+elong_max+" temps_graphe "+temps_graphe);
	    t_gg=temps_graphe;
	    temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
	    occupe=false;
	}
	void designe_couleurs(){
	    occupe=true;
	    if(grap[num_graphe]!=null){
		grap[num_graphe].setFont(appelant.times_gras_14);		
		if(nb_de_gaz==2){
		    grap[num_graphe].setColor(Color.red);
		    grap[num_graphe].drawString("Mariotte, gaz 0",right-left-120,bottom-top -100);
		    grap[num_graphe].setColor(Color.green);
		    grap[num_graphe].drawString("Mariotte, gaz 1",right-left-120,bottom-top -60);
		}else{
		    grap[num_graphe].setColor(Color.blue);
		    grap[num_graphe].drawString("Mariotte",right-left-120,bottom-top -100);
		}
	    }
	    designe_couleurs_2(num_graphe);
	    occupe=false;
	}
    }
    abstract class fantome extends graphes_spectres{
	int top=140, left=400,bottom = 540, right = 900;
	int posy_scale=1,larger_en_pixels=2;
	Graphics grfic[]=new Graphics[4];
	int posy_theor=0;
	long max_comptage=0;
	int posx=0,posy=0;
	float max_poisson[]=new float[2];
	float max_plot=0,echelle=1;
	int posy_prec_theor[]=new int [101];
	float parametre_equilibre[]=new float [2];
	String stt[]=new String [2];
	boolean	faire_le_second_plot_theor=false;
	public fantome(String s,int numg1){
	    super(s,numg1);
	    //setLocation(left,top);
	    System.out.println("cree spectre "+s);
	    stt[0]="Poisson";
	    stt[1]="Maxwell";
	    pack();
	    setVisible(true);
	}
	
	void met_l_echelle(Graphics gr,int nb_de_base){
	    gr.drawString("0",10,bottom-top-34);
	    gr.drawString(""+nb_de_base+" ",210,bottom-top-34);
	    gr.drawString(""+nb_de_base*2+" ",410,bottom-top-34);
	    gr.drawString(""+nb_de_base*3+" ",610,bottom-top-34);
	}
	public void raf(int isp,int num_spectre,int[][] posy_prec,long[][] comptage,float[][] proba_theor,int n_events_vus, Color col){
	    grfic[isp].setColor(Color.black);
	    int klim=100;
	    if(num_spectre<=0)
		klim=25;
	    if(num_spectre==2)
		klim=255;
	    if(num_spectre<=1){
		if(num_spectre==1)
		    max_plot=proba_theor[0][0];
		else if(num_spectre==0){
		    max_plot=0;
		    max_comptage=0;
		    for(int i=0;i<nb_de_gaz;i++){
			coco=max_poisson[i];
			if(coco>max_plot)
			    max_plot=coco;
			for(int k=30;k<=40;k++)
				max_comptage=Math.max(comptage[i][k],max_comptage);
		    }
		    faire_le_second_plot_theor=false;
		    if(nb_de_gaz==2)
			for(int k=0;k<=29;k++){
			    coco=proba_theor[0][k]-proba_theor[1][k];
			    cici=proba_theor[0][k]+proba_theor[1][k];
			    if(cici>(float)0.00001)
				if(Math.abs(coco/cici)>(float)0.01)
				    faire_le_second_plot_theor=true;
			}
		}
		echelle=1;
		
		if(num_spectre<=1)
		    echelle=2*max_plot/(float)(bottom-top-100);
		if(num_spectre==0)
		    echelle*=2;
	    }
	    if(n_events_vus/20*20==n_events_vus&&grfic[isp]!=null){
		appelant.eraserect(grfic[isp],60,0,bottom-top,right-left,Color.white);
		appelant.drawline_couleur(grfic[isp],0,bottom-top-30 , right-left, bottom-top-30, Color.black);
		if(num_spectre==1&&grfic[isp]!=null){
		    grfic[isp].drawString("0",10,bottom-top-34);
		    grfic[isp].drawString("En./particule",390,bottom-top-45);;
		}else if(num_spectre==2&&grfic[isp]!=null){
			met_l_echelle(grfic[isp],100);
		}else if(num_spectre==0&&grfic[isp]!=null){
		    grfic[isp].drawString(""+20*(1+gas[0].n_particules/2048),210,bottom-top-34);
		    grfic[isp].drawString(""+40*(1+gas[0].n_particules/2048),410,bottom-top-34);
		}
		if(grfic[isp]!=null)
		    grfic[isp].setColor(Color.red);
		//System.out.println("posy_scale_prec "+posy_scale_prec+" right "+right+" left "+left);
	    }
	    if(num_spectre==0)
		klim=40;
	    parametre_equilibre[0]=0;
	    parametre_equilibre[1]=0;
	    for(int k=0;k<=klim;k++){
		if(num_spectre==0){
		    posx=k*10-1+10;
		}else if(num_spectre==1)
		    posx=k*4-1+10;
		else if(num_spectre==2)
		    posx=k*2;
		for(int i=0;i<nb_de_gaz;i++){
		    Color ccooll=col;
		    if(i==1)
			ccooll=Color.blue;
		    if(num_spectre<=1){
			if(!(num_spectre==0&&k>=30)){
			    cece=gas[i].n_tot_part;
			    if(num_spectre==0)
				cece=64;//64 secteurs
			    cucu=(float)(comptage[i][k]+1)/(cece*n_events_vus);
			    posy=bottom-top-30-2*(int)Math.round(cucu/(float)echelle);
			}else{
			    cici=4*(float)(comptage[i][k]+1)/(float)max_comptage*max_plot*(float)(bottom-top-100);
			    posy=bottom-top-30-(int)Math.round(cici);
			}
		    }else
			posy=bottom-top-30-(int)comptage[i][k]*4;
		    if(num_spectre<=1&&!(num_spectre==0&&k>25)){
			if(num_spectre==1){
			    cucu=(float)(comptage[i][k]+1)/(cece*n_events_vus);
			    parametre_equilibre[i]+=(float)Math.pow(proba_theor[i][k]-cucu,2);
			}				
			posy_theor=bottom-top-30-2*(int)Math.round(proba_theor[i][k]/((float)echelle));
		    }
		    if(grfic[isp]!=null){
			dessine_pt_sp(Color.white,posx,posy_prec[i][k],isp);
			dessine_pt_sp(ccooll,posx,posy,isp);
			//if(num_spectre<=1&&!(num_spectre==0&&k>25)&&!(num_spectre==1&&paroi_mouvante)){
			if(num_spectre<=1&&!(num_spectre==0&&k>25&&grfic[isp]!=null)){
			    if(i==0)
				dessine_pt_sp(Color.red,posx,posy_theor,isp);
			    else if(num_spectre!=1&&faire_le_second_plot_theor)
				dessine_pt_sp(Color.green,posx+1,posy_theor,isp);
			}
		    }
		    posy_prec[i][k]=posy;
		}
	    }
	    if(num_spectre<=1&&grfic[isp]!=null)
		code_des_couleurs(num_spectre,grfic[isp]);
	    if(num_spectre==1&&sp_en_cin.grp_equilibre!=null){
		quel_graphe=sp_en_cin.grp_equilibre;
		//System.out.println(" parametre_equilibre[0] "+parametre_equilibre[0]+" parametre_equilibre[1] "+parametre_equilibre[1]);
		if(nb_de_gaz==1)
		    quel_graphe.demarre_dessine_graphe(parametre_equilibre[0]*(float)1.5,parametre_equilibre[0],(float)-1,1);
		else
		    quel_graphe.demarre_dessine_graphe(parametre_equilibre[0]+parametre_equilibre[1],parametre_equilibre[0],parametre_equilibre[1],2);
		
	    }
	    if(num_spectre==2&&sp_nb.grp_concentration!=null&&n_event/nb_evts_sans_calculs*nb_evts_sans_calculs==n_event){
		quel_graphe=sp_nb.grp_concentration;
		//System.out.println(" parametre_equilibre[0] "+parametre_equilibre[0]+" parametre_equilibre[1] "+parametre_equilibre[1]);
		if(nb_de_gaz==1)
		    quel_graphe.demarre_dessine_graphe(sp_nb.xx_moy[0]*(float)1.5,sp_nb.xx_moy[0],(float)-1,1);
		else
		    quel_graphe.demarre_dessine_graphe(Math.max(sp_nb.xx_moy[0],sp_nb.xx_moy[1]),sp_nb.xx_moy[0],sp_nb.xx_moy[1],2);
		
	    }
	}
	void dessine_pt_sp(Color col,int posx,int posy,int isp){
	    if(grfic[isp]!=null)
		grfic[isp].setColor(col);
	    toto_int=larger_en_pixels;
	    for (int j=-toto_int;j<=toto_int;j++)
		if(grfic[isp]!=null)
		    grfic[isp].drawLine(posx-toto_int,posy+j,posx+toto_int,posy+j);
	}
	void code_des_couleurs(int nu_grf,Graphics grf){
	    grf.setFont(appelant.times_gras_14);
	    if(nb_de_gaz==2){
		grf.setColor(Color.red);
		if(nu_grf==0&&grf!=null)
		    if(!faire_le_second_plot_theor)
			grf.drawString(stt[nu_grf],right-left-120,bottom-top -140);
		    else
			grf.drawString(stt[nu_grf]+",gaz 0",right-left-120,bottom-top -140);
		else if(nu_grf==1&&grf!=null)
		    grf.drawString(stt[nu_grf],right-left-120,bottom-top -140);
		if(nu_grf==0&&faire_le_second_plot_theor){
		    grf.setColor(Color.green);
		    grf.drawString(stt[nu_grf]+",gaz 1",right-left-120,bottom-top -100);
		}
	    }else if(grf!=null){
		grf.setColor(Color.red);
		grf.drawString(stt[nu_grf],right-left-120,bottom-top -140);
	    }
	    if(nb_de_gaz==2&&grf!=null){
		grf.setColor(Color.black);
		grf.drawString("gaz 0",right-left-100,bottom-top -120);
		grf.setColor(Color.blue);
		grf.drawString("gaz 1",right-left-100,bottom-top -80);
	    }
	}
    }
    class spectre_positions_moments extends fantome{
	boolean debut=true;int n_event_traites=0;
	long comptage_positions[][]=new long [2][101];
	boolean occupe=false;
	int occupation_par_secteur [][][]=new int [2][8][8];int occupation_secteur_h_ou_v[][]=new int [2][8];
	int occupation_par_secteur_part_interieur[][][]=new int [2][8][8];
	int posy_prec[][]=new int [2][101];;
	long numero_evt_prec[]=new long [2];long numero_evt[]=new long [2];
	boolean poisson_fait[]=new boolean[2];
	float poisson[][]=new float[2][101];
	spectre_positions_moments(String s, int numg1){
	    super(s,numg1+1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_spectre_a_virer=numg;			
			gas[0].incapaciter[26+numg]=false;		
		    };
		});		
	    top=40+numg*50;left=500+numg*20;bottom = 440+numg*50;right = 1000+numg*20;
	    grfic[numg]=getGraphics ();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grfic[numg].setColor(Color.blue);
	    grfic[numg].setFont(appelant.times_gras_24);
	    grfic[numg].drawString(" Attendez un moment svp. ",50,200);
	    //appelant.eraserect(grfic, 150,50,250,400,Color.white);
	    grfic[numg].setFont(appelant.times_gras_14);
	    poisson_fait[0]=false;
	    poisson_fait[1]=false;
	    if(numg1==1)
		for(int i=0;i<nb_de_gaz;i++)
		    gas[i].calcule_rayons_espace_des_p(gas[i].energie_cin_par_part);
	}
	void abandon(){
	    sp_pos_mom[numg-1].dispose();
	    sp_pos_mom[numg-1]=null;
	    quel_spectre[numg]=null;
	}
	void calcule_poisson(){
	    for(int j=0;j<nb_de_gaz;j++){
		poisson_fait[j]=true;
		float n_moyen=gas[j].n_tot_part/(float)64.;
		poisson[j][0]=1;
		for(int i=1;i<100;i++)
		    poisson[j][i]=poisson[j][i-1]*n_moyen/i;
		max_poisson[j]=0;
		for(int i=0;i<100;i++){
		    poisson[j][i] *= Math.exp(-n_moyen);
		    if(poisson[j][i]>max_poisson[j])
			max_poisson[j]=poisson[j][i];
		}
		if(gas[0].n_tot_part==2048){
		    for(int i=0;i<50;i++)
			poisson[j][i]=poisson[j][2*i]+poisson[j][2*i+1];
		    max_poisson[j]*=2;
		}
	    }
	}
	void incremente_comptages(boolean rond_interieur_initial,int h,int v,int nu_g){
	    occupe=true;
	    occupation_par_secteur[nu_g][h][v]++;
	    if(gas[nu_g].circulaire&&rond_interieur_initial){
		occupation_par_secteur_part_interieur[nu_g][h][v]++;
	    }
	    if(nb_de_gaz==1||numg==2)
		occupation_secteur_h_ou_v[nu_g][h]++;
	    else
		occupation_secteur_h_ou_v[nu_g][v]++;
	    occupe=false;
	}
	void calcule_comptages(int nu_gz){
	    occupe=true;
	    if(nu_gz==0)
		n_event_traites++;
	    numero_evt_prec[nu_gz]=numero_evt[nu_gz];
	    numero_evt[nu_gz]=n_event;
	    for (int i=0;i<8;i++){
		if(numero_evt[nu_gz]>numero_evt_prec[nu_gz]&&numero_evt_prec[nu_gz]!=0){
		    coco=Math.round((float)occupation_secteur_h_ou_v[nu_gz][i]/(numero_evt[nu_gz]-numero_evt_prec[nu_gz]));
		    comptage_positions[nu_gz][i+30]+=coco;
		    int kj=0; 
		    for (int j=0;j<8;j++){
			kj=(int)Math.round((float)occupation_par_secteur[nu_gz][i][j]/(numero_evt[nu_gz]-numero_evt_prec[nu_gz]));
			if(gas[0].n_tot_part==2048)
			    kj/=2;
			if(kj>100)
			    kj=100;
			comptage_positions[nu_gz][kj]++;
		    }
		    if(debut){
			debut=false;
			for (int j=0;j<8;j++)
			    System.out.println("nu_gz "+nu_gz+" j "+j+" numero_evt[nu_gz] "+numero_evt[nu_gz]+" numero_evt_prec[nu_gz] "+numero_evt_prec[nu_gz]+" comptage_positions[nu_gz][j] "+ comptage_positions[nu_gz][j]);
		    }
		}
	    }
	    for(int i=0;i<8;i++){
		occupation_secteur_h_ou_v[nu_gz][i]=0;
		for(int j=0;j<8;j++){
		    occupation_par_secteur[nu_gz][i][j]=0;
		    occupation_par_secteur_part_interieur[nu_gz][i][j]=0;
		}
	    }
	    occupe=false;
	}
	public void rafraichit(){
	    occupe=true;
	    top=240;bottom = 640;
	    if(!poisson_fait[0]||!poisson_fait[1]||particules_composees)
		calcule_poisson();
	    larger_en_pixels=2;
	    raf(numg,0,posy_prec,comptage_positions,poisson,n_event_traites,Color.black);
	    if(n_event_traites/20*20==n_event_traites&&grfic!=null){
		if(nb_de_gaz==1||numg==2)
		    grfic[numg].drawString(" A droite, nb moyen de particules pour 8 bandes radiales",10,40);
		else
		    grfic[numg].drawString(" A droite, nb moyen de particules pour 8 bandes verticales",10,40);
		grfic[numg].drawString("'equiprobables'.",10,54);
	    }
	    grfic[numg].setColor(Color.blue);
	    occupe=false;
	}	
    }
    class spectre_nombre_de_particules_composees  extends fantome{
	int n_event_traites=0;
	float hyper[]=new float[256];
	boolean occupe=false;
	int posy_prec[][]=new int [2][256];
	long comptage_n[][]=new long [2][256];
	graphe_concentration grp_concentration;
	float bidon_comptage_n[][]=new float [1][1];
	int n_pp_c=0;float proba_de_creation=0;
	float x_moy[] = new float[2];float x_moy_2[] = new float[2];long n_tot[] = new long[2];
	float xx_moy[] = new float[2];float xx_moy_2[] = new float[2];
	float somme_tot=0;
	spectre_nombre_de_particules_composees(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_spectre_a_virer=numg;
		    };
		});		
	    top=0;left=500;bottom = 120;right = 1000;
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grfic[numg]=getGraphics();
	    grp_concentration=new graphe_concentration("nombre de particules composées f.de t.",7);
	}
	void abandon(){
	    if(grp_concentration!=null)
		grp_concentration.abandon();
	    sp_nb.dispose();
	    sp_nb=null;
	    quel_spectre[numg]=null;
	}
	void incremente_compt(boolean remise_a_zero,int nu_gz){
	    occupe=true;
	    if(remise_a_zero){
		for (int i=0;i<256;i++)
		    comptage_n[nu_gz][i]=0;
		n_tot[nu_gz]=0;
		x_moy[nu_gz]=0;
		x_moy_2[nu_gz]=0;
	    }else{
		if(nu_gz==0)
		    n_event_traites++;
		comptage_n[nu_gz][gas[nu_gz].n_p_compos]++;
		n_tot[nu_gz] ++;
		toto_int=gas[nu_gz].n_p_compos;
		x_moy[nu_gz] +=toto_int;
		x_moy_2[nu_gz] +=toto_int*toto_int;
	    }
	    occupe=false;
	}
	public void rafraichit(){
	    occupe=true;
	    larger_en_pixels=1;
	    raf(numg,2,posy_prec,comptage_n,bidon_comptage_n,n_event_traites,Color.black);
	    for(int j=0;j<nb_de_gaz;j++){
		incremente_compt(false,j);
		if(n_event/gas[j].n_evt_eq_init[3]*gas[j].n_evt_eq_init[3]==n_event&&n_event!=0&&grfic!=null){
		    xx_moy[j] =x_moy[j]/n_tot[j];
		    gas[j].n_tot_part_moy_prec=gas[j].n_tot_part_moy;
		    gas[j].n_tot_part_moy=xx_moy[j]+(gas[j].n_particules-2*xx_moy[j]);
		    xx_moy_2[j] =x_moy_2[j]/n_tot[j];
		    xx_moy_2[j] -= xx_moy[j]*xx_moy[j];
		    xx_moy_2[j]=(float)Math.sqrt(xx_moy_2[j]);
		    coco=(float)Math.round(xx_moy[j]*(float)10.)/(float)10.;
		    caca=(float)Math.round(xx_moy_2[j]*(float)10.)/(float)10.;
		    appelant.eraserect(grfic[numg],(bottom-top)/2-40,250,(bottom-top)/2,right-left,Color.white);
		    grfic[numg].setColor(Color.black);
		    grfic[numg].drawString("moyenne "+coco+" + ou - "+caca,250,(bottom-top)/2+j*20);
		    incremente_compt(true,j);	
		}
	    }
	    if((n_event+1)/gas[0].n_evt_eq_init[3]*gas[0].n_evt_eq_init[3]==n_event+1)
		appelant.eraserect(grfic[numg],0,0,500,500,Color.white);
	    occupe=false;
	}
	class graphe_concentration extends graphes{
	    float elong[][]=new float [3][401];
	    int n_pts_graphe=0,temps_graphe=0;
	    point elong_max_min;
	    boolean occupe=false;
	    public graphe_concentration(String s,int numg1){
		super(s,numg1);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e){
			    num_graphe_a_virer=numg;
			};
		    });		
		System.out.println("cree graphe__concentration "+s+" num_graphe "+num_graphe);		//setLocation(left,top);
		elong_max_min=new point(point_zer0);
		grap[numg]= getGraphics(); 
		grap[numg].setColor(Color.blue);
		setVisible(true);
		designe_couleurs();
	    }	
	    void abandon(){
		grp_concentration.dispose();
		grp_concentration=null;
		quel_graf[numg]=null;
	    }
	    void dessine_graphe (float elo,int quelle_couleur){
		occupe=true;
		grap[num_graphe].setColor(Color.blue);
		if(!(quelle_couleur==2)){
		    point el_max=new point(elong_max_min);
		    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,elo);
		}
		t_gg=temps_graphe;
		temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
		occupe=false;		
	    }
	    void designe_couleurs(){
		occupe=true;
		if(nb_de_gaz==2){
		    designe_couleurs_2(num_graphe);
		    //System.out.println("designe_couleurs");
		}else if(particules_composees&&grap[num_graphe]!=null){
		    grap[num_graphe].setColor(Color.black);
		    grap[num_graphe].drawString("nb de particules composees",right-left-170,bottom-topp-80);
		}
		occupe=false;
	    }
	}	
    }
    class spectre_energies extends fantome{
	int n_event_traites=0;
	graphe_equilibre grp_equilibre;
	boolean occupe=false;
	boolean anfang=true;
	int posy_prec[][]=new int [2][101];
	long comptage[][]=new long [2][101];
	spectre_energies(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			if(sp_en_cin.grp_equilibre!=null)
			    num_graphe_a_virer=6;
			num_spectre_a_virer=numg;	
		    };
		});		
	    top=130;left=650;bottom = 530;right = 1150;
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grfic[numg]=getGraphics ();
	    grp_equilibre=new graphe_equilibre("parametre d'evolution vers l'equilibre",6);
	}
	void abandon(){
	    if(grp_equilibre!=null)
		grp_equilibre.abandon();
	    sp_en_cin.dispose();
	    sp_en_cin=null;
	    quel_spectre[numg]=null;
	}
	public void rafraichit(){
	    occupe=true;
	    top=240;bottom = 640;
	    larger_en_pixels=2;
	    if(particules_composees||paroi_mouvante||!gas[0].boqal.paroi.reflechissante)
		for(int i=0;i<nb_de_gaz;i++)
		    proba_maxw[i]=calcule_proba_maxwell(gas[i].energie_cin_par_part);
	    raf(numg,1,posy_prec,comptage,proba_maxw,n_event_traites,Color.black);
	    occupe=false;
	}
	void incremente_comptages(int nu_gz){
	    occupe=true;
	    if(nu_gz==0)
		n_event_traites++;
	    int kin_en=0;
	    for(int i=0;i<gas[nu_gz].n_particules+gas[nu_gz].n_particules_composees;i++){
		if(i<gas[nu_gz].n_particules&&gas[nu_gz].teilchen[i].num_particule_mere==-1||i>=gas[nu_gz].n_particules&&gas[nu_gz].teilchen[i]!=null){
		    kin_en=-1;float aab=0;
		    //on prend les canaux par 4	
		    aab=gas[nu_gz].teilchen[i].masse*gas[nu_gz].teilchen[i].vit.longueur_carre()/2;
		    if(!gas[nu_gz].avec_spin)
			kin_en=(int)(aab/(4*gas[nu_gz].den_par_ecart_d_energie));
		    else
			kin_en=(int)((aab+Math.pow(gas[nu_gz].teilchen[i].spin,2)/(2*gas[nu_gz].teilchen[i].moment_d_inertie))/(4*gas[nu_gz].den_par_ecart_d_energie));
		    if(kin_en>100)
			kin_en=100;
		    //System.out.println("kin_en"+kin_en+" i "+i+" teilchen[i].moment_d_inertie "+teilchen[i].moment_d_inertie+" boqal.surface "+boqal.surface);			
		    comptage[nu_gz][kin_en]++;
		}
	    }
	    occupe=false;
	}
	class graphe_equilibre extends graphes{
	    float elong[][]=new float [3][401];
	    int n_pts_graphe=0,temps_graphe=0;
	    point elong_max_min;
	    boolean occupe=false;
	    public graphe_equilibre(String s,int numg1){
		super(s,numg1);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e){
			    num_graphe_a_virer=numg;
			    //sp_en_cin=null;
			};
		    });	
		System.out.println("cree graphe_equilibre "+s+" num_graphe "+num_graphe);		//setLocation(left,top);
		elong_max_min=new point(point_zer0);
		top =130;left =750;bottom  = 380;right  = 1150;;	
		setSize(right-left,bottom-top);
		setLocation(left,top);	
		grap[numg]= getGraphics(); 
		grap[numg].setColor(Color.blue);
		setVisible(true);
		designe_couleurs();
	    }
	    void abandon(){
		grp_equilibre.dispose();
		grp_equilibre=null;
		quel_graf[numg]=null;
	    }	    
	    void dessine_graphe (float elo,int quelle_couleur){
		occupe=true;
		grap[num_graphe].setColor(Color.blue);
		if(!(quelle_couleur==2)){
		    point el_max=new point(elong_max_min);
		    elong_max_min=vas_y_dessine(quelle_couleur,elong,el_max,temps_graphe,elo);
		}
		t_gg=temps_graphe;
		temps_graphe=fin_dessine_graphe(t_gg,quelle_couleur);
		occupe=false;		
	    }
	    void designe_couleurs(){
		occupe=true;
		if(nb_de_gaz==2){
		    designe_couleurs_2(num_graphe);
		    //System.out.println("designe_couleurs");
		}else if(particules_composees&&grap[num_graphe]!=null){
		    grap[num_graphe].setColor(Color.black);
		    grap[num_graphe].drawString("nb de particules composees",right-left-170,bottom-topp-80);
		}
		occupe=false;
	    }
	}	
    }
    void elimination_reouverture_fenetres(boolean reouverture){
	System.out.println(" zntree elimination_reouverture_fenetres reouverture"+reouverture);
	for(int i=0;i<5;i++){
	    if(!(particules_composees&&i==3&&reouverture))
		open_close_reopen(st_spectre[i],false,true,reouverture);
	}
	for(int i=0;i<8;i++)
	    open_close_reopen(st_graf[i],false,true,reouverture);
	for(int i=0;i<4;i++){
	    open_close_reopen(st_comm[i],false,true,reouverture);
	}
    }
    void lancer_les_nouvelles_fenetres(){
	for(int i=0;i<nb_de_gaz;i++)
	    gas[i].raz();
	n_event=0;
	n_event_calculs=0;
	if(a_l_aide_svp==null)
	    open_close_reopen(st_comm[3],true,false,false);
	appel_al_aide(i_tr_aide);
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found<=9||gas[i].i_found==16||gas[i].i_found>=18&&gas[i].i_found<=19||gas[i].i_found==22||gas[i].i_found>=28&&gas[i].i_found<=31){
		if(gas[i].i_found!=11&&gas[i].i_found!=16&&!particules_composees)
		    open_close_reopen(st_spectre[0],true,false,false);
		if(gas[i].i_found==22)
		    open_close_reopen(st_spectre[0],true,false,false);
		gas[i].cree_particules();
	    }
	if(i_demarre==9&&isyst==0)
	    open_close_reopen(st_graf[5],true,false,false);
	for(int i=0;i<nb_de_gaz;i++)	
	    if(gas[i].i_found==6||gas[i].i_found==7){
		open_close_reopen(st_spectre[0],true,false,false);
		break;
	    }
	for(int i=0;i<nb_de_gaz;i++){
	    if(!gas[i].boqal.paroi.reflechissante||gas[i].i_found==8&&gas[i].avec_spin){
		open_close_reopen(st_graf[0],true,false,false);
		break;
	    }
	}
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==9)
		if(paroi_mouvante){
		    if(!un_des_2_osc_harm){
			open_close_reopen(st_graf[4],true,false,false);
			break;
		    }
		}else{
		    open_close_reopen(st_graf[4],false,true,false);
		    break;
		}	    
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==11&&!gas[i].boqal.paroi.reflechissante){
		open_close_reopen(st_graf[0],true,false,false);
		if(!un_des_2_osc_harm){
		    open_close_reopen(st_graf[4],true,false,false);
		    open_close_reopen(st_graf[3],true,false,false);
		}
		break;
	    }
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==17&&!un_des_2_osc_harm){
		open_close_reopen(st_graf[4],true,false,false);
		break;
	    }	
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==20){
		open_close_reopen(st_graf[0],true,false,false);
		break;
	    }	
	if(gas[0].i_found==21)
	    open_close_reopen(st_spectre[4],true,false,false);
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==23&&((paroi_mouvante)||!gas[i].boqal.paroi.reflechissante||particules_composees||gas[i].avec_spin)){
		open_close_reopen(st_graf[3],true,false,false);
		break;
	    }	
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==24&&particules_composees){
		open_close_reopen(st_spectre[3],true,false,false);
		break;
	    }	
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==25){
		open_close_reopen(st_graf[1],true,false,false);
		break;
	    }	
	for(int i=0;i<nb_de_gaz;i++)
	    if(gas[i].i_found==26||gas[i].i_found==27){
		open_close_reopen(st_spectre[1+gas[i].i_found-26],true,false,false);
		break;
	    }	
	for(int i=0;i<nb_de_gaz;i++){
	    boolean titilogic=false;
	    for (int j=0;j<4;j++){
		if(gas[i].i_found==28+j){
		    titilogic=true;
		    open_close_reopen(st_graf[3],false,true,false);
		    open_close_reopen(st_graf[4],false,true,false);
		    open_close_reopen(st_spectre[0],false,true,false);
		    open_close_reopen(st_spectre[1],false,true,false);
		    open_close_reopen(st_spectre[2],false,true,false);
		    break;
		}
	    }
	    if(titilogic)
		break;
	}
	System.out.println(" vers barre_des_menus ");
	if(!barre_menus_faite){
	    barre_menus();
	    System.out.println(" retour barre_des_menus ");
	}
    }
    class gaz{
	int n_particules0=64,n_p_compos=0,n_tot_part=0,n_part_petites_energies=0,n_particules_bleues=0;
	float energie_cin_petites=0,energie_cin_grosses=0,energie_pour_graphe=0;
	float e_par_petite_part=0,e_par_part_compos=0,e_par_part=0,e_tota_mo=0;
	int n_particules=n_particules0;float n_tot_part_moy=0,n_tot_part_moy_prec=0;
	//float energie_degagee=300,energie_interne=200;
	String str_menus[]=new String [33];
	int n_particules_composees=0;
	String str_menus_bis[]=new String [33];
	boolean menu_bis[]=new boolean[32];
	MenuItem prt_prf[]=new MenuItem [4];
	float ic_evt_pression[]=new float [20];
	float ic_evt_variation_entropie[]=new float [20];
	float ic_evt_energie[]=new float [20];
	float ic_evt_hypervolu[]=new float [20];
	boolean deb_calcul_hypervolu=true;
	float moy_sur_j=0;boolean avec_spin=false;
	int numero_a_revoir[]=new int [100];
	int nombre_a_revoir=0,nombre_a_revoir_cumule=0,nombre_acceptes=0,nombre_acceptes_second_pass=0,nombre_acceptes_cumule=0,nombre_acceptes_second_pass_cumule=0;
	point posit_avant[]=new point[100];point vit_avant[]=new point[100];
	boolean incapaciter[]=new boolean [33];
	float energie_degagee=000,energie_interne=200,e_pot=0,travaux_elementaires=0,travaux_elementaires0=0,e_pot_prec=0;
	float energie_totale_init=0,e_cin_prec=0,energie_totale_moyenne=0,energie_cin_par_part=0;
	int n_particules_premier_type=0;int n_particules_second_type=0;
	float rayon_moments_2[]=new float[8];int rayon_moments;
	float ray_moments_2[][]=new float[2][8];
	boolean initialisation_de_meme_energie=false,initialisation_maxwell=true,initialisation_plate=false;
	float dist_lim_pot=8,dist_lim_pot2=dist_lim_pot*dist_lim_pot,dist_lim_potmul2=dist_lim_pot*2;
	float xxx=-100000,yyy=-100000;
	//float dist_lim_pot=50;
	boolean bon_secteur=true;
	float rayon_fiduciel=0,dr_impact=0,dtang_impact=0,manque=0,longueur_v=0,posit_lg=0;
	float rapport_de_surfaces=0,dist_car_moy=0,dist_car_max=0;
	secteur sect_mom;
	objets_sauves e_objets_sauves;
	int n_petites_p=n_particules0;int indice=0,jndice=0;
	int n_evt_eq[]= new int [4];
	int n_evt_eq_init[]=new int [4];
	boolean cree_grosse=false,elimi_g=false,a_diffuse=false,a_reflechi=false;
	float energ_avant_defait=0,energ_apres_defait=0;
	float n_t_sur_p=0;int k_grosse;float sdm_cin_cree=0,sdm_cin_defait=0;
	float masse_particule0=(float)1.0;float masse_particule;
        float moment_d_inertie_particule=0;point r1_cm_au_choc;
	int nb_part_dans_le_rond_interieur_initial=0;
	float somme_des_spins_choc=0,somme_des_mom_orb_choc=0,s_abs_sp=0;int n_chocs_event=0;
	float facteur_spin=-(float)0.02,facteur_mm_cn_orb=(float)0.1;
	//float facteur_spin=-(float)0.02,facteur_mm_cn_orb=(float)0.3;
	float mom_cin_orbital_i=0;
	float  ne_spin=0, ie_spin=0;
	double e_orb_tot_i=0,e_spin_tot_i=0,e_orb_tot_f=0, e_spin_tot_f=0;
	float somme_spin_i=0, mom_cin_orbital_f=0;
	float s_angle_i=0,s_angle_f=0;
	float nvv=0,masse_inv=0,vnew=0,npp=0,nvv_f=0,dmom=0,ptns=0,ptis=0;
	float somme_spin_f=0;
	float energie_totale=0,energie_totale_prec=0,energie_precedente_moyenne=0,energie_cinetique=0;
	float energie_spins=0,energie_cinetique_p_compos=0;
	float energie_cin_premier_type=0,energie_cin_second_type=0;
	float sm=0,v_long=0,distance_transv=0,somme_ray=0;
	float diff_ecin_lab_cm=0;
	int n_interieur[]=new int[7];
	float nb_moyens_chocs_contre_paroi=0;
	float raideur0=(float)0.6;
	float raideur=raideur0;
	float pression_instant=0,pression=0,temperature=(float)1.;
	secteur i_paire_diff[]=new secteur[20];int n_diffus=0;
	float somme_libres_parcours_moyens=0;
	float libre_parcours_moyen=0,n_parcours_moyens=0;
	boolean osc_harm_limite,microboules,interaction_nulle=false;
	float hypervolu=0,d_hypervolu=0,hypervolu0=0,hypervolu_prec=0,var_rel_hyperv=0,hypervolu_pour_graphe=0,hypervolu_pour_graphe_prec=0,hypervolu_init=0,hypervolu_garde=0,hypervolu_instant=0;
	float rapport_hyperv=0;
	float surface_occupee_par_particules=0;
	point atf,atf1; point centre,dist_pos,dist_pos_prec,dif_vit_dt,un_rad_au_choc;
	point unite_transverse,unite,dposit;
	float en_par_particule0=(float)1000.,e_au_depart=0;
	float en_par_particule0_max=2*en_par_particule0;
	float en_par_particule=en_par_particule0;
	float den_par_ecart_d_energie=en_par_particule/100;
	int n_ecarts_energie=4;
        float barriere_creation=2*(float)1000.;
	//float barriere_annih=4*(float)1000.;
	float barriere_annih=(float)1000.;
	partic teilchen[]=new partic[2072];
	int rayon_particule0=3,rayon_particule=3,rayon_particule_max=6;
	int ryn_prtc[]=new int [2];
	int nb_de_types_de_surf_part=1;
	boolean elimination[]=new boolean[1024];
	boolean creation_particule;		float rho_part=0;
	point r_particule,v_particule;
	int num_gaz;float dQ=0,dQ_sur_T=0;
	bocal boqal;boolean circulaire;boolean begin_gaz=true;
	int i_found=-1;
	represente geschwind;
	gaz(boolean cercle1,point centre1, float dim1,float dim2,int num_gaz1,systeme ruf1){
	    System.out.println("deb gaz");
	    ruf=ruf1;
	    centre=new point(centre1);
	    circulaire=cercle1;
	    masse_particule=masse_particule0;
	    if(i_demarre==4)
		masse_particule*=2;
	    num_gaz=num_gaz1;
	    System.out.println("gaz,num_gaz"+num_gaz);
	    n_particules_composees=0;
	    for(int i=0;i<20;i++){
		ic_evt_variation_entropie[i]=0;
		ic_evt_pression[i]=0;
		ic_evt_hypervolu[i]=0;
		ic_evt_energie[i]=0;
	    }
	    for(int j=0;j<4;j++){
		n_evt_eq[j]=0;
		n_evt_eq_init[j]=10;
	    }
	    //r_particule=new point(point_zer0);
	    r_particule=new point(point_zer0);
	    v_particule=new point(point_zer0);
	    for(int i=0;i<20;i++)
		i_paire_diff[i]=new secteur(0,0,8);
	    if(i_demarre==8)
		str_menus0[0]="multiplier le nb de particules par 2,max a 512";
	    for(int i=0;i<33;i++){
		incapaciter[i]=false;
		if(num_gaz==0){
		    str_menus[i]=str_menus0[i];
		    str_menus_bis[i]=str_menus_bis0[i];
		}else{
		    str_menus[i]=str_menus0[i]+" ";
		    str_menus_bis[i]=str_menus_bis0[i]+" ";
		}
	    }
	    for(int i=0;i<4;i++)
		prt_prf[i]=new MenuItem(str_menus[28+i]);
	    osc_harm_limite=false;interaction_nulle=false;
	    microboules=true;
	    nb_moyens_chocs_contre_paroi=0;
	    sect_mom=new secteur(-1,-1,8);
	    unite=new point(point_zer0);
	    for(int i=0;i<100;i++){
		posit_avant[i]=new point(point_zer0);
		vit_avant[i]=new point(point_zer0);
	    }

	    unite_transverse=new point(point_zer0);
	    dposit=new point(point_zer0);
	    diff_ecin_lab_cm=0;
	    graphe_energie_part_compos=particules_composees;
	    graphe_energie_cinetique=(i_demarre==3);
	    toto=new point(point_zer0);toto1=new point(point_zer0);atf=new point(point_zer0);atf1=new point(point_zer0);tutu=new point(point_zer0);tata=new point(point_zer0);titi=new point(point_zer0);
	    dist_au_cercle=new point(point_zer0);posit_au_cercle=new point(point_zer0);

	    dist_pos=new point(point_zer0);dist_pos_prec=new point(point_zer0);dif_vit_dt=new point(point_zer0);
	    if(circulaire)
		boqal=new bocal_circulaire(centre,(int)dim1);
	    else
		boqal=new bocal_rectangulaire(centre,dim1,dim2);
	    rayon_particule=rayon_particule0;
	    microboules=true;
	    interaction_nulle=false;
	    osc_harm_limite=false;
	    particules_composees=false;
	    paroi_mouvante=false;
	    if(i_demarre==9&&isyst==0){
		microboules=true;
		osc_harm_limite=false;interaction_nulle=false;
	    }
	    if(i_demarre<=1){
		n_particules=n_particules0*16;
		if((i_demarre==0)&&(num_gaz==1)){
		    microboules=false;
		    osc_harm_limite=false;interaction_nulle=true;
		}
	    }
	    if(i_demarre==1&&num_gaz==0)
		rayon_particule=2;
	    if(i_demarre==2){
		initialisation_maxwell=false;
		initialisation_plate=true;
		n_particules=n_particules0*16;
		microboules=false;
		osc_harm_limite=false;
		if(num_gaz==0)
		    microboules=true;
		else{
		    osc_harm_limite=true;
		    un_des_2_osc_harm=true;
		    dist_lim_pot=16;
		    raideur=(float)0.6;
		}
		rayon_particule=3;
		boqal.paroi.reflechissante=true;
	    }
	    if(i_demarre==8){
		n_particules=n_particules0*8;
		particules_composees=true;
		avec_spin=false;
	    }
	    if(i_demarre==9){
		if(isyst==1){
		    n_particules=n_particules0*8;
		    rayon_particule=rayon_particule0;
		}else{
		    n_particules=n_particules0*32;
		    //n_particules=n_particules0/32;
		    rayon_particule=rayon_particule0;
		}
	    }
	    if(i_demarre==8)
		en_par_particule0=500;
	    if(i_demarre==3||i_demarre>=5&&i_demarre<=7){
		//n_particules=n_particules0*2;
		n_particules=n_particules0*16;
		if(i_demarre==3){
		    avec_spin =false; // c'est inversé dans l'action_correspondante.
		    action_correspondante(8);
		}
		if((i_demarre==6||i_demarre==7)&&num_gaz==0){
		    n_particules_premier_type=n_particules/2;
		    n_particules_second_type=n_particules/2;
		}
		if(i_demarre==5){
		    System.out.println(" begin_gaz avant 11"+begin_gaz);
		    action_correspondante(11);
		    action_correspondante(23);
		}
	    }
	    if(i_demarre==4){
		rayon_particule=2;
		//rayon_particule=rayon_particule0;
		paroi_mouvante=true;
		calcul_hypervolu=true;
		if(num_gaz==0){
		    n_particules=n_particules0*16;
		    //n_particules=n_particules0*8;
		    en_par_particule0=3000;
		}else{
		    n_particules=n_particules0*8;
		    //		    n_particules=n_particules0*2;
		    en_par_particule0=3000;
		}
	    }
	    //n_particules_init=n_particules   ;
	    System.out.println("num_gaz "+num_gaz+" i_demarre "+i_demarre+ " rayon_particule "+rayon_particule+ " microboules "+microboules+" interaction_nulle "+interaction_nulle+" paroi_mouvante "+paroi_mouvante+" en_par_particule0 "+en_par_particule0);	    
	    definit_surf_perim_libre(i_demarre==6&&num_gaz==0);
	    
	    cree_particules();

	}
	void definit_surf_perim_libre(boolean ttt){
	    nb_de_types_de_surf_part=1;
	    if(!ttt)
		ryn_prtc[0]=rayon_particule;
	    else
		ryn_prtc[0]=rayon_particule-1;
	    boqal.surf_perim_libre(0,ryn_prtc[0]);
	    if(ttt||i_demarre==8||particules_composees){
		nb_de_types_de_surf_part=2;
		if(!ttt)		
		    ryn_prtc[1]=rayon_particule*2;
		else
		    //**ryn_prtc[1]=rayon_particule+1;
		    ryn_prtc[1]=rayon_particule;
		boqal.surf_perim_libre(1,ryn_prtc[1]);
	    }
	}
	public void cree_particules(){
	    en_par_particule=en_par_particule0;
	    energie_cin_par_part=en_par_particule;
	    den_par_ecart_d_energie=en_par_particule/100;
	    e_au_depart=n_particules*en_par_particule;
	    for(int j=0;j<2072;j++)
		teilchen[j]=null;
	    proba_maxw[num_gaz]=calcule_proba_maxwell(en_par_particule);
	    System.out.println("num_gaz "+num_gaz+" n_particules "+n_particules+" en_par_particule "+en_par_particule);
	    rayon_grosse_particule=2*rayon_particule;
	    moment_d_inertie_particule=masse_particule*(float)Math.pow(rayon_particule,2)/2;
	    nb_part_dans_le_rond_interieur_initial=0;
	    travail_frottement=0;	    
	    if(!machina_vapore){
		n_particules_composees=0;n_p_compos=0;n_petites_p=n_particules;
		System.out.println("num_gaz "+num_gaz+" initialisation_de_meme_energie "+initialisation_de_meme_energie);
	    }
	    if(!initialisation_maxwell){
		for (int i=0;i<n_particules;i++)
		    if(initialisation_plate)
			energ_init[i]=en_par_particule*2*(float)(i+1)/n_particules;
		    else if(initialisation_de_meme_energie)
			energ_init[i]=en_par_particule;
	    }else {
		//float e=-den_par_ecart_d_energie/2;

		float e_centre_bin=-den_par_ecart_d_energie*2;
		float e_mini_du_bin=0;
		int n_part_vues=0;float ener_t=0;
		for (int j=0;j<=100;j++){
		    e_mini_du_bin=e_centre_bin+2*den_par_ecart_d_energie;
		    e_centre_bin += 4*den_par_ecart_d_energie;
		    float dn_dans_de=proba_maxwell[j]*n_particules;
		    int entier_dn=(int)dn_dans_de;
		    float residu=dn_dans_de-entier_dn;
		    if((float)Math.random()<residu)
			entier_dn++;
		    int k=0;float derniere_e_du_bin=0;
		    for (int i=0;i<entier_dn;i++)
			if(n_part_vues+i<n_particules){
			    if(entier_dn>1)
				energ_init[n_part_vues+i]=e_mini_du_bin+(i+(float)0.5)*2*(e_centre_bin-e_mini_du_bin)/entier_dn;
			    else
				energ_init[n_part_vues+i]=e_centre_bin;
			    ener_t += energ_init[n_part_vues+i];
			    derniere_e_du_bin=energ_init[n_part_vues+i];
			    k++;
			}
		    if(num_gaz==1){
			int kkk=n_part_vues+k;
			System.out.println("j "+j+" e_centre_bin "+e_centre_bin+" dn_dans_de "+dn_dans_de+" entier_dn "+entier_dn+" ener_t "+ener_t);
			System.out.println(" energ_init[n_part_vues] "+energ_init[n_part_vues]+" derniere_e_du_bin "+derniere_e_du_bin+"k "+k+" n_part_vues "+n_part_vues+" kkk "+kkk );
		    }
		    n_part_vues += k;
		}

		float de_sur_e=(e_au_depart-ener_t)/ener_t;float e=0;
		for (int i=0;i<n_part_vues;i++){
		    energ_init[i] *= (1+de_sur_e);
		    e += energ_init[i];
		}
		for (int i=n_part_vues;i<n_particules;i++)
		    energ_init[i]=0;
		System.out.println("e_totale "+e);
	    }
	    float eee=0;
	    for (int i=0;i<n_particules/2;i+=2){
		eee=energ_init[n_particules-1-i];
		energ_init[n_particules-1-i]=energ_init[i];
		energ_init[i]=eee;
	    }
	    float mmnrt=masse_particule*(float)Math.pow(rayon_particule,2)/2;
	    //	    somme_spin_0=(float)Math.sqrt(en_par_particule/(float)3.*2*mmnrt);
	    
	    //	    System.out.println("*****rayon_particule "+rayon_particule);
	    float bg=boqal.x_gauche+rayon_particule_max;
	    float ll=boqal.x_droite-boqal.x_gauche-2*rayon_particule_max;
	    float bh=boqal.hauteur-2*rayon_particule_max;
	    if(i_demarre==9)
		n_particules_bleues=0;
	    for (int i=0;i<n_particules;i++){ 
		if(circulaire){
		    rho_part=(boqal.rayon-rayon_particule-(float)0.1)*appelant.sqrtrndm[i];
		    r_particule.assigne(rho_part*appelant.cosphi_hasard[i],rho_part*appelant.sinphi_hasard[i]);
		    if(rho_part>195.99999)
			System.out.println(" &&&&&&&&&&&&&rho_part "+rho_part+" boqal.rayon "+boqal.rayon+" rayon_particule "+rayon_particule);
		    
		}else{
		    r_particule.assigne(bg+ll*appelant.rndm1[i],-bh/2+bh*appelant.rndm2[i]);
			
		}
		if(!((i_demarre==6||i_demarre==7)&&num_gaz==0)&&i_demarre!=9){
		    if((i_demarre==6||i_demarre==7)&&num_gaz==1)
			genere_part_masse(masse_particule,0,i);
		    else
			genere_part_masse(masse_particule,i-i/16*16,i);
		}else if(i_demarre==9){
		    float r2=rho_part*rho_part;
		    for (int k=0;k<=7;k++){
			if(r2<=boqal.rayon_secteur_2[0][k]){
			    int icc=0;
			    if(k<=1&&isyst==0){
				icc=7;
				n_particules_bleues++;
			    }
			    genere_part_masse(masse_particule,icc,i);
			    break;
			}
		    }
		}else
		    if(i<n_particules_premier_type)
			if(i_demarre==7&&num_gaz==0)
			    genere_part_masse(masse_particule,6,i);
			else
			    genere_part_rayon(2,6,i);
		    else
			if(i_demarre==7&&num_gaz==0)
			    genere_part_masse(2*masse_particule,0,i);
			else
			    genere_part_rayon(3,0,i);
	    }
	    if(osc_harm_limite){
		System.out.println(" tototototototo ");
		for (int i=0;i<n_particules;i++) 
		    teilchen[i].posit_prec.assigne(teilchen[i].posit);
	    }
	    if(osc_harm_limite){
		travaux_elementaires0=energie_potentielle();
		travaux_elementaires=travaux_elementaires0;
	    }
	    energie_totale_init=energ_tot();
	    energie_totale=energie_totale_init;
	    energie_precedente_moyenne=energie_totale;
	    boqal.energie_cedee=0;
	    hypervolu_instant=0;
	    surface_occupee_par_particules=srfc_occupee_par_particules();
	    System.out.println(" num_gaz "+num_gaz+" surface_occupee_par_particules "+surface_occupee_par_particules);
	    rapport_de_surfaces=surface_occupee_par_particules/boqal.surface;
	    if(particules_composees){
		n_petites_p=n_particules;
		n_p_compos=0;
	    }
	    hypervolu=hyperv(energie_totale);
	    hypervolu_init=hypervolu;
	    System.out.println(" num_gaz "+num_gaz+" hypervolu_init "+hypervolu_init);
	    if(avec_spin){
		float ss=spin_tot();
		System.out.println("energie_totale "+energie_totale+" spin_total "+ss+" s_abs_sp "+s_abs_sp);
	    }else
		System.out.println("energie_totale "+energie_totale);
	    int n_reculs=0;
	    for(int indi=0;indi<n_particules+n_particules_composees;indi++){
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null)
		    n_reculs+=teilchen[indi].recule_en_cas_de_recouvrement(true);
	    }
	    reinitialsations_dans_systeme(num_gaz);
	    System.out.println("n_reculs "+n_reculs);
	    begin_gaz=false;
	}
	void initialise_vers_l_equilibre(boolean meme,boolean plate,boolean maxw){
	    for(int j=0;j<4;j++)
		n_evt_eq[j]=0;
	    initialisation_de_meme_energie=meme; 
	    initialisation_plate=plate;
	    initialisation_maxwell=maxw;
	}
	void raz(){
	    System.out.println(" entree raz   num_gaz "+num_gaz);
	    if(grp_dS_sur_dQ_sur_T!=null)
		boqal.energie_cedee=0;
	    en_par_particule=en_par_particule0;
	    den_par_ecart_d_energie=en_par_particule/100;
	    somme_libres_parcours_moyens=0;
	    n_parcours_moyens=0;
	    for(int j=0;j<4;j++)
		n_evt_eq[j]=0;
	    for(int k=0;k<n_evt_eq_init[0];k++){
		ic_evt_pression[k]=0;
		ic_evt_hypervolu[k]=0;
		ic_evt_variation_entropie[k]=0;
	    }
	    e_tota_mo=0;
	    hypervolu_instant=0;
	    deb_calcul_hypervolu=true;
	    elimination_reouverture_fenetres(true);
	}
	void genere_part_masse(float mass,int icouleur,int i){
	    float rho_v=0;
	    if(!avec_spin)
		rho_v=(float)Math.sqrt(2*energ_init[i]/mass);
	    else
		rho_v=(float)Math.sqrt(2*energ_init[i]*0.6666666/mass);
	    v_particule.assigne(rho_v*appelant.cosphi_v_hasard[i],rho_v*appelant.sinphi_v_hasard[i]);
	    
	    if(osc_harm_limite||interaction_nulle)
		teilchen[i]=new particule(r_particule,mass,rayon_particule,v_particule,icouleur,i);
	    else
		teilchen[i]=new boule(r_particule,mass,rayon_particule,v_particule,icouleur,i);
	    if(!teilchen[i].interieur_du_bocal()){
		teilchen[i].posit.print("mauvaise initialisation teilchen[i].posit ");
		teilchen[4000]=null;	
	    }
	    if(avec_spin){
		teilchen[i].moment_d_inertie=mass*(float)Math.pow(rayon_particule,2)/2;
		teilchen[i].spin=(float)Math.sqrt(2*energ_init[i]*0.333333*teilchen[i].moment_d_inertie);
		if((float)Math.random()<0.5)
		    teilchen[i].spin=-teilchen[i].spin;	     
	    }
	    if(circulaire){
		teilchen[i].rond_interieur_initial=r_particule.longueur_carre()<boqal.rayon_secteur_2[0][1];
		if(teilchen[i].rond_interieur_initial)
		    nb_part_dans_le_rond_interieur_initial++;
	}
	}
	void genere_part_rayon(int rayo,int icouleur,int i){
	    float rho_v=0;
	    if(!avec_spin)
		rho_v=(float)Math.sqrt(2*energ_init[i]/masse_particule);
	    else
		rho_v=(float)Math.sqrt(2*energ_init[i]*0.6666666/masse_particule);
	    v_particule.assigne(rho_v*appelant.cosphi_v_hasard[i],rho_v*appelant.sinphi_v_hasard[i]);
	    if(osc_harm_limite||interaction_nulle)
		teilchen[i]=new particule(r_particule,masse_particule,rayo,v_particule,icouleur,i);
	    else
		teilchen[i]=new boule(r_particule,masse_particule,rayo,v_particule,icouleur,i);
	    if(!teilchen[i].interieur_du_bocal()){
		teilchen[i].posit.print("mauvaise initialisation "+rayo+"teilchen[i].posit ");
	    }
	}

	float calcule_elong_graphe(float[] jc_evt,int quel_parametre){//0 pour equilibre,1 por dS/(dQ/T,2 pour pression, 3 pour hypervolume)
	    float elo=0;
	    if(n_evt_eq[quel_parametre]>=n_evt_eq_init[quel_parametre]){
		for(int j=0;j<n_evt_eq_init[quel_parametre];j++)
		    elo+=jc_evt[j];
		elo/=n_evt_eq_init[quel_parametre];
		for(int j=0;j<n_evt_eq_init[quel_parametre]-1;j++)
		    jc_evt[j]=jc_evt[j+1];
		if(quel_parametre==0){
		    jc_evt[n_evt_eq_init[quel_parametre]-1]=energie_totale_moyenne;
		}else if(quel_parametre==1){
		    jc_evt[n_evt_eq_init[quel_parametre]-1]=rapport_hyperv;
		}else if(quel_parametre==2)
		    jc_evt[n_evt_eq_init[quel_parametre]-1]=pression_instant;
		else if(quel_parametre==3)
		    jc_evt[n_evt_eq_init[quel_parametre]-1]=hypervolu_instant;
	    }else{
		if(quel_parametre==0){
		    jc_evt[n_evt_eq[0]]=energie_totale_moyenne;
		}else if(quel_parametre==1){
		    jc_evt[n_evt_eq[1]]=rapport_hyperv;
		    //System.out.println(" n_evt_eq[1] "+n_evt_eq[1]+" num_gaz "+num_gaz+" rapport_hyperv "+rapport_hyperv);
		}else if(quel_parametre==2){
		    jc_evt[n_evt_eq[2]]=pression_instant;
		    //System.out.println(" n_evt_eq[2] "+n_evt_eq[2]+" num_gaz "+num_gaz+" pression_instant "+pression_instant);
		}else if(quel_parametre==3){
		    jc_evt[n_evt_eq[3]]=hypervolu_instant;
		    //System.out.println(" n_evt_eq[3] "+n_evt_eq[3]+" num_gaz "+num_gaz+" hypervolu "+hypervolu+" jc_evt[n_evt_eq[3]] "+jc_evt[n_evt_eq[3]]);
		}
		n_evt_eq[quel_parametre]++;
	    }
	    return elo;
	}
	void calcule_rayons_espace_des_p(float e_par_part){
	    rayon_moments_2[7]=0;float e=0;
	    for(int i=0;i<7;i++){
		e=-e_par_part*(float)Math.log(1-(float)(i+1)/8);
		rayon_moments_2[i]=2*masse_particule*e;
		rayon_moments=(int)Math.sqrt(rayon_moments_2[i]);
		if(i_demarre==7&&num_gaz==0){
		    ray_moments_2[0][i]=2*masse_particule*e;
		    ray_moments_2[1][i]=4*masse_particule*e;
		}
	    }
	}

  	void menu_bar(MenuBar menu_syst){
	    String chaine_suppl_titre;
	    System.out.println("nb_de_gaz "+nb_de_gaz);
	    if(nb_de_gaz==1)
		chaine_suppl_titre="";
	    else
		if(num_gaz==0)
		    chaine_suppl_titre=",gaz 0";
		else
		    chaine_suppl_titre=",gaz 1";
	    Menu menu_interactions= new Menu("Interaction"+chaine_suppl_titre);
	    if((!avec_spin)&&(!particules_composees)&&(!paroi_mouvante)){
		Menu portee_profondeur=new Menu("Osc.harm. de portee limitee");
		for(int i=0;i<4;i++){
		    prt_prf[i].addActionListener(ruf);			  
		    portee_profondeur.add(prt_prf[i]);
		}
		menu_interactions.add(portee_profondeur);
	    }
	    for(int i=18;i<=19;i++){
		incapaciter[i]=(i==19&&((avec_spin)||(particules_composees)));
		remplis_ruf(i,false,menu_interactions);
	    }
	    menu_syst.add(menu_interactions);
	    
	    Menu actions=new Menu("Changements"+chaine_suppl_titre);
	    Menu paroi_gaz= new Menu("Paroi(s) du(des) gaz");

	    if(nb_de_gaz==2&&boqal.paroi.reflechissante)
		remplis_ruf(9,paroi_mouvante,paroi_gaz);
	    /*
	    if(paroi_mouvante)
		remplis_ruf(12,avec_amortissement,paroi_gaz);
	    */
	    //if(i_demarre==5||i_demarre==7||i_demarre==8)
	    if(!paroi_mouvante&&!particules_composees){
		remplis_ruf(11,boqal.paroi.reflechissante,paroi_gaz);
		Menu varier_T_paroi_gaz= new Menu("Varier sa temperature");
		remplis_ruf(32,false,varier_T_paroi_gaz);
		remplis_ruf(13,false,varier_T_paroi_gaz);
		paroi_gaz.add(varier_T_paroi_gaz);
	    }
	    actions.add(paroi_gaz);
	    Menu varie_nb=new Menu("Varier le nombre de particules");
	    varie_nb.setEnabled(true);
	    for(int i=0;i<=1;i++)
		remplis_ruf(i,false,varie_nb);
	    actions.add(varie_nb);
	    if(!(i_demarre==6&&num_gaz==0)){
		Menu varie_rayon=new Menu("Varier le rayon des particules");
		varie_rayon.setEnabled(true);
		for(int i=2;i<=3;i++)
		    remplis_ruf(i,false,varie_rayon);
		actions.add(varie_rayon);
	    }
	    Menu varie_energie=new Menu("Varier l'energie des particules");
	    varie_energie.setEnabled(true);
	    remplis_ruf(10,false,varie_energie);
	    remplis_ruf(15,false,varie_energie);
	    actions.add(varie_energie);
	    Menu varie_masse=new Menu("Varier la masse des particules");
	    varie_masse.setEnabled(true);
	    remplis_ruf(4,false,varie_masse);
	    remplis_ruf(5,false,varie_masse);
	    actions.add(varie_masse);
	    Menu evolu=new Menu("Evolution vers l'equilibre");
	    evolu.setEnabled(true);
	    if(particules_composees||i_demarre==9&&isyst==1||isyst==1)
		evolu.setEnabled(false);
	    if(i_demarre!=4)
		for(int i=6;i<=7;i++)		
		    remplis_ruf(i,false,evolu);
	    actions.add(evolu);
	    //incapaciter[8]=particules_composees||osc_harm_limite||interaction_nulle||i_demarre==6&&num_gaz==0||i_demarre==9&&isyst==1;
	    incapaciter[8]=paroi_mouvante||particules_composees||osc_harm_limite||i_demarre==6&&num_gaz==0||i_demarre==9&&isyst==1;
	    if(i_demarre!=6&&i_demarre!=7&&i_demarre!=4&&i_demarre!=3)
		remplis_ruf(8,avec_spin,actions);
	    if(nb_de_gaz==2&&i_demarre==4)
		remplis_ruf(14,false,actions);
	    //incapaciter[15]=avec_spin||(i_demarre==6||i_demarre==7)&&num_gaz==0||osc_harm_limite||i_demarre==9&&isyst==1;
	    //remplis_ruf(15,particules_composees,actions);
	    remplis_ruf(16,false,actions);	    
	    menu_syst.add(actions);
	}
	void remplis_ruf(int i,boolean bis,Menu graph){
	    MenuItem iteqq;
	    if(bis)
		iteqq=new MenuItem(str_menus_bis[i]);
	    else
		iteqq=new MenuItem(str_menus[i]);
	    if(machina_vapore)
		incapaciter[i]=true;
	    System.out.println("i "+i+" incapaciter[i] "+incapaciter[i]+" i_demarre "+i_demarre);
	    iteqq.setEnabled(!(isyst==1||incapaciter[i]));
	    iteqq.addActionListener(ruf);
	    graph.add(iteqq);
	    if(dernier_i_trouve==1)
		System.out.println("i "+i+" dernier_i_trouve "+dernier_i_trouve+" incapaciter[i] "+incapaciter[i]);
	}
	boolean selectionne_actions_interaction(){
	    boolean trouve=false;
	    for(int i=0;i<33;i++){
		if(command==str_menus[i]||i>=8&&i<=13&&command==str_menus_bis[i]){
		    trouve=true;
		    System.out.println("*****************$$$i_trouve "+i);
		    if(i>=8&&i<=12)
			menu_bis[i]=(command==str_menus_bis[i]);
		    i_found=i;
		    action_correspondante(i);
		    break;
		}
	    }
	    System.out.println(" trouve "+trouve);
	    return trouve;
	}
	void action_correspondante(int i_trouve){
	    dernier_i_trouve=i_trouve;
	    i_tr_aide=i_trouve;
	    if(i_tr_aide>=28&&i_tr_aide<32)
		i_tr_aide=28;
	    barre_menus_faite=false;
	    boolean possible=false;
	    System.out.println("action correspondante i_trouve "+i_trouve+" bandeau_x "+bandeau_x+" bandeau_y "+bandeau_y);
	    if(i_trouve<=1||i_trouve<=6&&i_trouve<=8&&i_demarre!=3||i_trouve==25){
		reinitialsations_dans_systeme(num_gaz);
	    }
	    grp_c.setColor(Color.red);
	    n_event_calculs=0;
	    if(i_trouve==0){
		if(n_particules==1024)
		    if(rayon_particule>2){
			rayon_particule=2;
			definit_surf_perim_libre(i_demarre==6&&num_gaz==0);
		    }
		possible=true;
		if(i_demarre==8){
		    System.out.println(" n_p_compos*2 "+n_p_compos*2+" n_petites_p "+n_petites_p+" n_particules "+n_particules);
		    if(n_p_compos*2+n_petites_p>256)
			possible=false;
		}else if(n_particules==2048)
		    possible=false;
		else
		    possible=est_ce_possible(n_p_compos,n_petites_p*2,rayon_particule,0);
		if(possible){
		    n_particules*=2;
		    if((i_demarre==6||i_demarre==7)&&num_gaz==0){
			n_particules_premier_type *=2;
			n_particules_second_type *=2;
		    }
		    st_bandeau="Votre nb de particules est maintenant de"+n_particules;
		    n_particules_composees=0;
		}else
		    st_bandeau="Votre nombre de particules serait trop grand.";
	    }
	    if(i_trouve==1){
		possible=est_ce_possible(n_p_compos,n_petites_p/2,rayon_particule,0);
		if(possible){
		    n_particules/=2;
		    if((i_demarre==6||i_demarre==7)&&num_gaz==0){
			n_particules_premier_type /=2;
			n_particules_second_type /=2;
		    }
		    st_bandeau="Votre nb de particules est maintenant de "+n_particules;
		    n_particules_composees=0;
		}else
		    st_bandeau="Votre nombre de particules serait trop petit.";
	    }
	    if(i_trouve==2){
		possible=est_ce_possible(n_p_compos,n_petites_p,rayon_particule+1,0);
		if(possible&&rayon_particule<6){
		    rayon_particule+=1;
		    definit_surf_perim_libre(i_demarre==6&&num_gaz==0);
		    st_bandeau="Votre rayon de base des particules est maintenant de "+rayon_particule;
		}else
		    st_bandeau="Votre rayon de particule serait trop grand.";
	    }
	    if(i_trouve==3){
		possible=est_ce_possible(n_p_compos,n_petites_p,rayon_particule-1,0);
		if(possible&&rayon_particule>1){
		    rayon_particule-=1;
		    definit_surf_perim_libre(i_demarre==6&&num_gaz==0);
		    st_bandeau="Votre rayon de particules est maintenant de "+rayon_particule+" pixels.";
		}else
		    st_bandeau="Votre rayon de particule serait trop petit.";
	    }
	    if(i_trouve==4){
		if(masse_particule< 3.4999){
		    masse_particule*=2;
		    for (int i=0;i<n_particules+n_particules_composees;i++)
			if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			    teilchen[i].masse *=2;
			    teilchen[i].vit.multiplie_cst(1/racine_de_2);
			}
		    st_bandeau="Votre masse de particules est maintenant de "+masse_particule;
		}else
		    st_bandeau="Votre masse de particule ne peut etre superieure à 4";
	    }
	    if(i_trouve==5){
		if(masse_particule>= 0.4999){
		    masse_particule/=2;
		    for (int i=0;i<n_particules+n_particules_composees;i++)
			if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			    teilchen[i].masse /=2;
			    teilchen[i].vit.multiplie_cst(racine_de_2);
			}
		    st_bandeau="Votre masse de particules est maintenant de"+masse_particule;
		}else
		    st_bandeau="Votre masse de particule ne peut etre inferieure à 0.25";
	    }
	    if(i_trouve==6||i_trouve==7){
		
		if(i_trouve==6){
		    initialise_vers_l_equilibre(true,false,false);
		    if(nb_de_gaz==2)
			gas[1-num_gaz].initialise_vers_l_equilibre(true,false,false);
		    st_bandeau="Toutes les particules ont au depart la meme energie. ";
		}
		if(i_trouve==7){
		    initialise_vers_l_equilibre(false,true,false);
		    if(nb_de_gaz==2)
			gas[1-num_gaz].initialise_vers_l_equilibre(false,true,false);
		    st_bandeau="Vous avez une distribution plate en energie. ";
		}
		if(nb_de_gaz==2)//pour le gaz en question, c'est fait ensuite
		    gas[1-num_gaz].cree_particules();
	    }
	    if(i_trouve==8){
		avec_spin = !avec_spin;
		graphe_energie_cinetique=avec_spin;
		if(avec_spin){
		    if(pendant_construction_syst_ou_gaz)
			open_close_reopen(st_graf[0],false,false,true);
		    if(nb_de_gaz==1)
			st_bandeau="Vos particules ont maintenant un spin";
		    else
			st_bandeau="Les particules du gaz "+num_gaz+" ont un spin";
		    initialise_vers_l_equilibre(false,false,true);
		}
		else
		    st_bandeau="Vos particules n'ont plus de spin.";
		graphe_energie_des_spins=avec_spin;
	    }
	    if(i_trouve==9){
		paroi_mouvante=!paroi_mouvante;
		calcul_hypervolu=!paroi_mouvante;
		if(!paroi_mouvante){
		    st_bandeau="La paroi entre les deux gaz est fixe";
		}else{
		    deb_calcul_hypervolu=true;
		    st_bandeau="La paroi entre les deux gaz est mobile";
		}
	    }
	    if(i_trouve==10||i_trouve==15){
		if(i_trouve==10){
		    if(en_par_particule0<(en_par_particule0_max+(float)0.1)/2){
			en_par_particule0 *=2;
			e_au_depart=en_par_particule0*n_tot_part;
			den_par_ecart_d_energie=en_par_particule0/100;
			for (int i=0;i<n_particules+n_particules_composees;i++)
			    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
				teilchen[i].vit.multiplie_cst((float)Math.sqrt(2.));
			System.out.println("multipliee command  "+command);
			st_bandeau="L' energie par particule a ete multipliee par 2";
		    }else
			st_bandeau="L' energie est deja maximum.";
		}else{
		    en_par_particule0 /=2;
		    e_au_depart=en_par_particule0*n_tot_part;
		    den_par_ecart_d_energie=en_par_particule0/100;
		    for (int i=0;i<n_particules+n_particules_composees;i++)
			if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
			    teilchen[i].vit.multiplie_cst(1/(float)Math.sqrt(2.));
		    System.out.println("divisee command "+command);
		    st_bandeau="L' energie par particule a ete divisee par 2";
		}
	    }
	    if(i_trouve==11){
		boqal.paroi.reflechissante=!boqal.paroi.reflechissante;
		if(nb_de_gaz==2)
		    gas[1-num_gaz].boqal.paroi.reflechissante=boqal.paroi.reflechissante;
		System.out.println(" boqal.paroi.reflechissante "+boqal.paroi.reflechissante);
		if(!boqal.paroi.reflechissante){
		    calcul_hypervolu=true;
		    if(pendant_construction_syst_ou_gaz){
			open_close_reopen(st_graf[0],false,false,true);
			if(!un_des_2_osc_harm){
			    open_close_reopen(st_graf[4],false,false,true);
			    open_close_reopen(st_graf[3],false,false,true);
			}
		    }		    
		}
		System.out.println("boqal.paroi.en_cin_paroi "+boqal.paroi.en_cin_paroi+" boqal.paroi.masse_element_paroi "+boqal.paroi.masse_element_paroi);
		
	    }
	    if(i_trouve==12){
		avec_amortissement=!avec_amortissement;
		if(avec_amortissement)
		    k_paroi=1/d_temps;
		else
		    k_paroi=0;
		System.out.println(" avec_amortissement"+avec_amortissement+" k_paroi "+k_paroi);
		
	    }
	    if(i_trouve==13){
		boqal.paroi.reflechissante=false;
		if(boqal.paroi.en_cin0_paroi>boqal.paroi.temperature_minimum){
		    boqal.paroi.en_cin0_paroi/=2;
		    boqal.paroi.en_cin_paroi=boqal.paroi.en_cin0_paroi;
		    st_bandeau="La temperature de la paroi est maintenant "+boqal.paroi.en_cin_paroi;
		}else
		    st_bandeau="La temperature de la paroi est inchangee:"+boqal.paroi.en_cin0_paroi;
		boqal.paroi.en_cin_paroi=boqal.paroi.en_cin0_paroi;
	    }
	    if(i_trouve==32){		
		boqal.paroi.reflechissante=false;
		if(boqal.paroi.en_cin0_paroi<boqal.paroi.temperature_maximum){
		    boqal.paroi.en_cin0_paroi*=2;
		    boqal.paroi.en_cin_paroi=boqal.paroi.en_cin0_paroi;
		    st_bandeau="La temperature de la paroi est mainrenant "+boqal.paroi.en_cin0_paroi;
		}else{
		    st_bandeau="La temperature de la paroi est inchangee:"+boqal.paroi.en_cin0_paroi;
		    boqal.paroi.en_cin_paroi=boqal.paroi.en_cin0_paroi;
		}
		//System.out.println("boqal.paroi.temperature_minimum "+boqal.paroi.temperature_minimum);
	    }
	    if(i_trouve==14){
		machine_a_vapeur();
		barre_menus_faite=false;
		calcul_hypervolu=false;
	    }
	    if(i_trouve==17){
		System.out.println(" niveau 26 boqal.paroi.reflechissante "+boqal.paroi.reflechissante); 
		if(graf_entropie==null){
		    deb_calcul_hypervolu=true;
		    System.out.println(" (((((graf_entropie))))) "+graf_entropie);
		    calcul_hypervolu=true;
		    gas[0].incapaciter[17]=true;
		    if(nb_de_gaz==2)
			gas[1].incapaciter[17]=true;
		}
	    }
	    if(i_trouve==18||i_trouve==19){
		if(i_trouve==18){
		    osc_harm_limite=false;interaction_nulle=false;microboules=true;
		}
		if(i_trouve==19){
		    osc_harm_limite=false;microboules=false;interaction_nulle=true;
		}
		if(nb_de_gaz==2)
		    un_des_2_osc_harm=gas[1-num_gaz].osc_harm_limite;
	    }
	    if(i_trouve==20){
		incapaciter[20]=true;
	    }
	    if(i_trouve==21){
		incapaciter[21]=true;
	    }
	    if(i_trouve==22){
		incapaciter[22]=true;
	    }
	    if(i_trouve==23){
		System.out.println("nnnnn boqal.paroi.reflechissante "+boqal.paroi.reflechissante);
		if(paroi_mouvante||!boqal.paroi.reflechissante||particules_composees||avec_spin){
		    System.out.println(" niveau 23 boqal.paroi.reflechissante "+boqal.paroi.reflechissante); 
		    calcul_hypervolu=true;
		    System.out.println(" (((((grp_dS_sur_dQ_sur_T pas nul))))) "+grp_dS_sur_dQ_sur_T);
		}else{
		    if(pendant_construction_syst_ou_gaz){
			open_close_reopen(st_graf[3],false,true,false);
			gas[0].incapaciter[23]=false;
		    }
		    calcul_hypervolu=false;
		}
	    }
	    if(i_trouve==24){
		if(particules_composees)
		    if(sp_nb==null){
			incapaciter[24]=true;
		    }
	    }
	    if(i_trouve==25){
		System.out.println(" niveau 26 boqal.paroi.reflechissante "+boqal.paroi.reflechissante); 
		if(graf_pression==null){
		    System.out.println(" (((((graf_entropie))))) "+graf_entropie);
		    gas[0].incapaciter[25]=true;
		    if(nb_de_gaz==2)
			gas[1-num_gaz].incapaciter[25]=true;
		}
	    }
	    if(i_trouve==26||i_trouve==27){
		System.out.println(" niveau 27 num_gaz "+num_gaz); 
		incapaciter[i_trouve]=true;
	    }
	    for (int i=0;i<4;i++){
		if(i_trouve==28+i){
		    osc_harm_limite=true;un_des_2_osc_harm=true;
		    microboules=false;interaction_nulle=false;
		    //raideur=raideur0*(float)10.;
		    dist_lim_pot=portee[i];
		    raideur=profdr[i];
		    break;
		}
	    }
	}
	float spin_fct_energ(partic pt_n,float de,float ptnspin){
	    float spin = (float)Math.sqrt(2*pt_n.moment_d_inertie*(pt_n.e_spin-de));
	    if(ptnspin<0)
		spin = -spin;
	    return spin;
	}
	public void calcule_et_ecrit_chiffres(){
	    //grp_c.setFont(appelant.times_gras_14);
	    float mom_cin_tot_spin=0;
	    if(avec_spin)	    
		for (int i=0;i<n_particules+n_particules_composees;i++)
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
			mom_cin_tot_spin += teilchen[i].spin; 
	    energie_totale=energ_tot();
	    if(!boqal.paroi.reflechissante||paroi_mouvante&&!machina_vapore){
		en_par_particule=(energie_cinetique+energie_spins)/n_tot_part;
		if(sp_en_cin!=null){
		    den_par_ecart_d_energie=en_par_particule/100;
		}
		if(n_event/200*200==n_event&&n_event!=0){
		    boqal.paroi.energie_moyenne_recul=0;
		    toto_int=0;
		    for(int i=0;i<300;i++){
			boqal.paroi.energie_moyenne_recul+=boqal.paroi.energie_particule_recul[i];
			toto_int+=boqal.paroi.nombre_particules_recul[i];
			//if(boqal.paroi.nombre_particules_recul[i]!=0)
			// System.out.println("i "+i+" boqal.energie_particule_recul[i] "+boqal.paroi.energie_particule_recul[i]+" boqal.nombre_particules_recul[i] "+boqal.paroi.nombre_particules_recul[i]);
			boqal.paroi.nombre_particules_recul[i]=0;
			boqal.paroi.energie_particule_recul[i]=0;
		    }
		    boqal.paroi.energie_moyenne_recul/=toto_int;
		    if(boqal.paroi.energie_moyenne_recul>boqal.paroi.en_cin0_paroi)
			boqal.paroi.en_cin_paroi*=(boqal.paroi.en_cin0_paroi/boqal.paroi.energie_moyenne_recul);
		    System.out.println("tttttttttt boqal.paroi.energie_moyenne_recul "+boqal.paroi.energie_moyenne_recul+" toto_int "+toto_int+" boqal.paroi.en_cin_paroi "+boqal.paroi.en_cin_paroi+" en_par_particule0 "+en_par_particule0);
		}
	    }
	    dist_car_moy=0;
	    if(i_demarre==9)
		for(int i=0;i<n_particules+n_particules_composees;i++)
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
			if(teilchen[i].icouleur==7)
			    dist_car_moy+=teilchen[i].posit.longueur_carre();
	    if(sp_en_cin!=null)
		sp_en_cin.incremente_comptages(num_gaz);
	    for(int i=0;i<n_particules+n_particules_composees;i++){
		if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
		    totologic=teilchen[i].i_choc!=-1;
		    if(osc_harm_limite)
			totologic=totologic&&teilchen[i].i_choc_prec==-1;
		    
		    if(totologic||teilchen[i].reflexion){
			if(!(interaction_nulle&&!teilchen[i].a_deja_reflechi)){
			    somme_libres_parcours_moyens+=teilchen[i].d_depuis_dernier_choc;
			    n_parcours_moyens+=1;
			}
			teilchen[i].d_depuis_dernier_choc=0;
			if(interaction_nulle&&teilchen[i].reflexion)
			    teilchen[i].a_deja_reflechi=true;
		    }
		} 
	    }
	    if(i_demarre==9&&isyst==0){
		if(dist_carr_moy==null)
		    open_close_reopen(st_graf[5],true,false,false);
		dist_car_moy/=n_particules_bleues;
		quel_graphe=dist_carr_moy;
		System.out.println(" quel_graphe "+quel_graphe);				st1_dist_carr="Limite";
		st2_dist_carr="particules bleues";
		quel_graphe.demarre_dessine_graphe(dist_car_max,dist_car_max,dist_car_moy,2);
		
	    }
	    for (int k=0;k<7;k++)
		n_interieur[k]=0;
	    if(((n_event==5)||(n_event/200*200==n_event))&&(nb_de_gaz==1||nb_de_gaz==2&&num_gaz==1))
		    met_les_titres();
	    if(n_event_calculs==nb_evts_sans_calculs-1){
		int ee=(int)Math.round(energie_totale);
		//temperature=energie_cinetique/n_tot_part;
		temperature=energie_cinetique/n_particules;
		appelant.eraserect(grp_c,bottom_temperature-10,num_gaz*200+160,bottom_temperature,num_gaz*200+300,Color.white);
		grp_c.setColor(Color.black);
		grp_c.drawString(""+ee+",   "+(int)Math.round(temperature),num_gaz*200+160,bottom_temperature);
		if(num_gaz==1){
		    appelant.eraserect(grp_c,bottom_temperature-10,num_gaz*200+300,bottom_temperature,num_gaz*200+400,Color.white);
		    grp_c.setColor(Color.black);
		    float a=(float)Math.round(energie_totale/gas[0].energie_totale*(float)100.)/(float)100.;
		    grp_c.drawString(""+a,num_gaz*200+300,bottom_temperature);
		    if(i_demarre==4){
			appelant.eraserect(grp_c,bottom_temperature-10,num_gaz*200+400,bottom_temperature,num_gaz*200+550,Color.white);
			grp_c.setColor(Color.black);
			a=(float)Math.round(energie_totale+gas[0].energie_totale);
			grp_c.drawString(""+a,num_gaz*200+400,bottom_temperature);
		    }
		}
		pression_instant=boqal.paroi.dif_impuls_cumulee/(nb_evts_sans_calculs*d_temps)/boqal.perimetre;
		pression=calcule_elong_graphe(ic_evt_pression,2);
		//System.out.println(" pression_instant "+pression_instant+" boqal.paroi.dif_impuls_cumulee "+boqal.paroi.dif_impuls_cumulee+" nb_evts_sans_calculs "+nb_evts_sans_calculs+" d_temps "+d_temps+" boqal.perimetre "+boqal.perimetre);
		float aaa=(float)Math.round(pression*(float)100.)/(float)100.;
		n_t_sur_p=n_tot_part*temperature/pression;
		float n_t=(float)Math.round(n_t_sur_p*(float)10.)/(float)10.;
		if((float)Math.abs(n_t_sur_p)<(float)900000.){
		    appelant.eraserect(grp_c,bottom_pression-10,num_gaz*200+160,bottom_pression,num_gaz*200+250,Color.white);
		    grp_c.setColor(Color.black);
		    grp_c.drawString(""+aaa,num_gaz*200+160,bottom_pression);
		    if(num_gaz==1){
			appelant.eraserect(grp_c,bottom_pression-10,num_gaz*200+250,bottom_pression,num_gaz*200+300,Color.white);
			grp_c.setColor(Color.black);
			float a=(float)Math.round(pression/gas[0].pression*(float)100.)/(float)100.;
			grp_c.drawString(""+a,num_gaz*200+250,bottom_pression);
		    }
		    
		    
		    appelant.eraserect(grp_c,bottom_nTsp-10,num_gaz*200+200,bottom_nTsp,num_gaz*200+350,Color.white);
		    grp_c.setColor(Color.black);
		    float bqs=(float)Math.round(boqal.surface);

		    grp_c.drawString(""+n_t+"("+bqs+")",num_gaz*200+200,bottom_nTsp);
		    if(num_gaz==1){
			appelant.eraserect(grp_c,bottom_nTsp-10,num_gaz*200+330,bottom_nTsp,num_gaz*200+450,Color.white);
			grp_c.setColor(Color.black);
			float a=(float)Math.round(n_t_sur_p/gas[0].n_t_sur_p*(float)100.)/(float)100.;
			grp_c.drawString(""+a,num_gaz*200+360,bottom_nTsp);
		    }
		}
		if(n_parcours_moyens>0.1)
		    libre_parcours_moyen=somme_libres_parcours_moyens/n_parcours_moyens;
		boqal.paroi.dif_impuls_cumulee=0;
		boqal.dnb_chocs_contre_paroi=0;
		//System.out.println("sp_pos_mom[0] "+sp_pos_mom[0]+" sp_pos_mom[1] "+sp_pos_mom[1]);
		for (int j=0;j<2;j++)
		    if(sp_pos_mom[j]!=null)
			sp_pos_mom[j].calcule_comptages(num_gaz);
		if(avec_spin){
		    cece=(float)Math.round(mom_cin_tot_spin*(float)10.)/(float)10.;
		    appelant.eraserect(grp_c,bottom_spins-10,num_gaz*200+78+58,bottom_spins,num_gaz*200+300,Color.white);
		    grp_c.setColor(Color.black);
		    grp_c.drawString(""+cece,num_gaz*200+220,bottom_spins);
		}
		if(grp_en_tot!=null&&nb_de_gaz==1){
		    //if(grp_dS_sur_dQ_sur_T==null)
		    quel_graphe=grp_en_tot;
		    st3_energie=" ";
		    if(!particules_composees){
			if(i_demarre==3||avec_spin&&nb_de_gaz==1){
			    st1_energie="Totale ";
			    st2_energie="cinétique ";
			    st3_energie="spins ";
			    quel_graphe.demarre_dessine_graphe(energie_totale,energie_spins,energie_cinetique,3);
			    //System.out.println(" energie_totale "+energie_totale+" energie_spins "+energie_spins+" energie_cinetique "+energie_cinetique);
			}else if(i_demarre==5){
			    st1_energie="Totale";
			    st2_energie="Prise a la paroi";
			    quel_graphe.demarre_dessine_graphe(energie_totale,energie_totale,boqal.energie_cedee,2);
			}else if((i_demarre==6||i_demarre==7||i_demarre==8)&&gas[0].boqal.paroi.reflechissante){
			    if(num_gaz==0){
				st1_energie="Totale, gaz 0";
				st2_energie="premier type, gaz 0";
				st3_energie="second type, gaz 0";	    
				quel_graphe.demarre_dessine_graphe(energie_totale,energie_cin_second_type*n_particules_second_type,energie_cin_premier_type*n_particules_premier_type,3);
			    }
			}
		    }else if(i_demarre==8){
			st1_energie="moy/part.composee";
			st2_energie="moy/petite part.";
			st3_energie="moy/part. initiale";
			e_par_petite_part=(energie_cinetique-energie_cinetique_p_compos)/n_petites_p;
			e_par_part_compos=energie_cinetique_p_compos/n_p_compos;
			e_par_part=energie_cinetique/n_particules;
			quel_graphe.demarre_dessine_graphe(e_par_part_compos,e_par_part,e_par_petite_part,3);
		    }else if(i_demarre!=5&&!(i_demarre==3||gas[0].avec_spin)&&!(i_demarre==6||i_demarre==7))
			if(gas[0]!=null){
			    st1_energie="Totale";
			    quel_graphe.demarre_dessine_graphe(gas[0].energie_totale*2,gas[0].energie_totale,(float)-1,1);
			}
		}		    
	    }
	    if(!particules_composees){
		if((!boqal.paroi.reflechissante||paroi_mouvante)&&!un_des_2_osc_harm){
		    if(calcul_hypervolu){
			hypervolu=hyperv(energie_totale);
			if(deb_calcul_hypervolu&&n_event>=5){
			    gas[num_gaz].hypervolu0=hypervolu;
			    energie_precedente_moyenne=energie_totale;
			    System.out.println(" num_gaz "+num_gaz+" n_event "+n_event);
			    deb_calcul_hypervolu=false;
			}
			e_tota_mo+=energie_totale;
			hypervolu_instant+=hypervolu;
			if(n_event/n_evt_eq_init[3]*n_evt_eq_init[3]==n_event){
			    e_tota_mo/=n_evt_eq_init[3];
			    hypervolu_instant/=n_evt_eq_init[3];
			    va_faire_graphe_dS_et_dSsurdQsurT(e_tota_mo);
			    e_tota_mo=0;
			    hypervolu_instant=0;
			}
		    }
		}
	    }else if(sp_nb!=null)
		sp_nb.rafraichit();
	    grp_c.setColor(Color.black);
	    appelant.eraserect(grp_c,bottom_libre_p-10,num_gaz*200+230,bottom_libre_p,num_gaz*200+300,Color.white);
	    float l=(float)Math.round(libre_parcours_moyen*(float)10.)/(float)10.;
	    
	    grp_c.setColor(Color.black);
	    grp_c.drawString(""+l,num_gaz*200+230,bottom_libre_p);
	    if(num_gaz==1){
		appelant.eraserect(grp_c,bottom_libre_p-10,num_gaz*200+330,bottom_libre_p,num_gaz*200+400,Color.white);
		grp_c.setColor(Color.black);
		float a=(float)Math.round(libre_parcours_moyen/gas[0].libre_parcours_moyen*(float)100.)/(float)100.;
		grp_c.drawString(""+a,num_gaz*200+350,bottom_libre_p);
	    }
	    appelant.eraserect(grp_c,bottom_n_part-10,num_gaz*250+130,bottom_n_part,num_gaz*200+200,Color.white);
	    grp_c.setColor(Color.black);
	    //int n_pp=n_petites_p+n_p_compos;
	    //System.out.println("*****************$$$n_tot_part "+n_tot_part);
	    if(!particules_composees)
		grp_c.drawString(""+n_tot_part,num_gaz*250+130,bottom_n_part);
	    else{
		appelant.eraserect(grp_c,bottom_n_part-10,0,bottom_n_part,600,Color.white);
		grp_c.setColor(Color.black);
		grp_c.drawString("    n petites.part     "+n_petites_p+"    n part.composees     "+n_p_compos,0,bottom_n_part);
	    }
	}


	void va_faire_graphe_dS_et_dSsurdQsurT(float en_tot_moy){
	    hypervolu_pour_graphe_prec=hypervolu_pour_graphe;
	    hypervolu_pour_graphe=calcule_elong_graphe(ic_evt_hypervolu,3);
	    if(n_event>=120){
		if(n_event==120&&graf_entropie==null)
		    open_close_reopen(st_graf[4],true,false,false);
		quel_graphe=graf_entropie;
		if(nb_de_gaz==1){
		    var_rel_hyperv=(hypervolu_pour_graphe-hypervolu0)/hypervolu0;
		    System.out.println("£££££££££n_event "+n_event+" hypervolu_instant "+hypervolu_instant+" hypervolu_pour_graphe "+hypervolu_pour_graphe+" hypervolu0 "+hypervolu0+" var_rel_hyperv "+var_rel_hyperv);
		    quel_graphe.demarre_dessine_graphe(var_rel_hyperv*2,var_rel_hyperv,(float)-1,1);
		}else{
		    //if(n_event>=2*n_evt_moy&&n_event/n_evt_moy*n_evt_moy==n_event){
		    var_rel_hyperv=(hypervolu_pour_graphe-hypervolu_pour_graphe_prec)/(gas[0].hypervolu0+gas[1].hypervolu0);
		    System.out.println("n_event "+n_event+" num_gaz "+ num_gaz+" hypervolu "+hypervolu+" hypervolu0 "+hypervolu0+" hypervolu_pour_graphe "+hypervolu_pour_graphe+" hypervolu_pour_graphe_prec "+hypervolu_pour_graphe_prec+" var_rel_hyperv "+var_rel_hyperv);
		    if(num_gaz==1)
			quel_graphe.demarre_dessine_graphe(var_rel_hyperv+gas[0].var_rel_hyperv,gas[0].var_rel_hyperv,var_rel_hyperv,3);
		}
	    }
	    if(!boqal.paroi.reflechissante){
		dQ = (en_tot_moy-energie_precedente_moyenne);
		System.out.println(" niveau 2 dQ"+dQ+" en_tot_moy "+en_tot_moy+" energie_precedente_moyenne "+energie_precedente_moyenne+" energie_totale_moyenne "+energie_totale_moyenne);
		if(Math.abs(dQ)>(float)0.0001){
		    float deg_lib=n_tot_part-1;
		    //if(avec_spin)deg_lib*=1.5;
		    //float TTT= ((en_tot_moy+energie_precedente_moyenne)/2/deg_lib);
		    //float TTT=en_tot_moy/deg_lib;
		    float TTT=(en_tot_moy+energie_precedente_moyenne)/2/deg_lib;
		    dodo=(double)(energie_precedente_moyenne);
		    dQ_sur_T=dQ/TTT;
		    dede=(double)(dQ)/dodo;
		    didi=dede*dede;
		    if(grp_dS_sur_dQ_sur_T!=null){
			if(((float)Math.abs(dQ)>0.000001)&&(energie_precedente_moyenne>0.001)){
			    if(!particules_composees)
				d_hypervolu =(float)((n_tot_part-1)*dede*(1-dede/2+didi/3-didi*dede/4+didi*didi/5));//du cinquième ordre
			    else
				d_hypervolu=(hypervolu_pour_graphe-hypervolu_pour_graphe_prec);
			    if((float)Math.abs(dQ_sur_T)>0.){
				rapport_hyperv=d_hypervolu/dQ_sur_T;
				System.out.println(" num_gaz "+num_gaz+" hypervolu_pour_graphe "+gas[0].hypervolu_pour_graphe+" hypervolu_pour_graphe_prec "+gas[0].hypervolu_pour_graphe_prec+" hypervolu "+gas[0].hypervolu+" hypervolu_prec "+gas[0].hypervolu_prec+" d_hypervolu "+gas[0].d_hypervolu+" dQ "+dQ+" TTT "+TTT+"dQ_sur_T "+dQ_sur_T); 
				quel_graphe=grp_dS_sur_dQ_sur_T;
				hypervolu_garde=calcule_elong_graphe(ic_evt_variation_entropie,1);
				System.out.println("rapport_hyperv "+rapport_hyperv+" hypervolu_garde "+hypervolu_garde+" en_tot_moy"+en_tot_moy+"energie_precedente_moyenne"+energie_precedente_moyenne+" n_tot_part "+n_tot_part);
				if(nb_de_gaz==1)
				    quel_graphe.demarre_dessine_graphe(hypervolu_garde,hypervolu_garde,-1,1);
				else if(num_gaz==1)
				    quel_graphe.demarre_dessine_graphe(hypervolu_garde,gas[0].hypervolu_garde,hypervolu_garde,2);
			    }
			}
			energie_precedente_moyenne=en_tot_moy;
			hypervolu_prec=hypervolu;
		    }
		}
	    }
	}
	float mom_cin_tot(){
	    float m_cin=0;
	    for (int i=0;i<n_particules+n_particules_composees;i++)
		if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
		    if(teilchen[i].num_particule_mere==-1)
			m_cin+=teilchen[i].masse*teilchen[i].posit.produit_vectoriel(teilchen[i].vit);
	    //else 
	    //System.out.println(" i "+i+" teilchen[i].num_particule_mere "+teilchen[i].num_particule_mere);
	    return m_cin;
	}
	float spin_tot(){
	    float m_cin=0;s_abs_sp=0;
	    for (int i=0;i<n_particules+n_particules_composees;i++)
		if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
		    if(teilchen[i].num_particule_mere==-1){
			m_cin+=teilchen[i].spin;
			s_abs_sp +=(float)Math.abs(teilchen[i].spin);
		    }
	    //else 
	    //System.out.println(" i "+i+" teilchen[i].num_particule_mere "+teilchen[i].num_particule_mere);
	    return m_cin;
	}
	float log_volume_entropique(int n_part,float energie,float mass){
	    return appelant.part_d_entropie[n_part]+n_part*((float)Math.log(energie*2*mass));
	}
	float hyperv(float energie_totale_m){
	    if(particules_composees&&(n_p_compos>0)){
		float ent=0,srfc_ccp_pr_partcl=0;int n_pet_p=0;
		for(int n_p_cmp=0;n_p_cmp<256;n_p_cmp++){
		    if(sp_nb.comptage_n[num_gaz][n_p_cmp]>0){
			n_pet_p=n_particules-2*n_p_cmp;
			srfc_ccp_pr_partcl=pi*(n_pet_p*rayon_particule*rayon_particule+n_p_cmp*rayon_grosse_particule*rayon_grosse_particule);
			coco=appelant.part_d_entropie[n_pet_p+n_p_cmp]+n_pet_p*(float)Math.log(2*masse_particule)+n_p_cmp*(float)Math.log(4*masse_particule)+(n_pet_p+n_p_cmp-1)*(float)Math.log(energie_totale_m)+(float)Math.log((float)(n_pet_p+n_p_cmp));//+n_tot_part*(float)Math.log(boqal.surface-srfc_ccp_pr_partcl));
			ent+=sp_nb.comptage_n[num_gaz][n_p_cmp]*coco;
			//ent+=sp_nb.comptage_n[num_gaz][n_p_cmp]*coco/(n_pet_p+n_p_cmp);
		    }
		}
		System.out.println(" energie_totale_m "+energie_totale_m+" ent "+ent); 
		return ent;
		
	    }else if(i_demarre==7&&num_gaz==0){
		return appelant.part_d_entropie[n_tot_part]+n_tot_part/2*((float)Math.log(2*masse_particule)+(float)Math.log(4*masse_particule))+(n_tot_part-1)*(float)Math.log(energie_totale_m)+(float)Math.log((float)(n_tot_part))+n_tot_part*(float)Math.log(boqal.surface-surface_occupee_par_particules);
	    }else if(avec_spin){
		toto_int=n_particules+n_particules/2;
		return appelant.part_d_entropie[toto_int]+n_particules*(float)Math.log(2*masse_particule)+n_particules/2*(float)Math.log(2*moment_d_inertie_particule)+(toto_int-1)*(float)Math.log(energie_totale_m)+(float)Math.log((float)(toto_int))+n_tot_part*(float)Math.log(boqal.surface-surface_occupee_par_particules);
	    }else
		//attention masse pour melange
		return log_volume_entropique(n_tot_part,energie_totale_m,masse_particule)+(float)Math.log(n_tot_part/energie_totale_m)+n_tot_part*(float)Math.log(boqal.surface-surface_occupee_par_particules);
	    //System.out.println("energie_totale_m "+energie_totale_m+" energie_precedente "+energie_precedente +" hypervolu_precedent "+hypervolu_precedent+" dQ "+dQ);
	}
	float energie_potentielle(){
	    coco=dist_lim_pot*dist_lim_pot;
	    float ee_ppot=0;
	    for (int j=0;j<n_particules+n_particules_composees;j++)
		if(j<n_particules&&teilchen[j].num_particule_mere==-1||j>=n_particules&&teilchen[j]!=null)
		    for (int i=j+1;i<n_particules+n_particules_composees;i++)
			if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			    dist_pos.assigne_soustrait(teilchen[i].posit,teilchen[j].posit);
			    if(dist_pos.longueur_carre()<coco)
				ee_ppot+=raideur*dist_pos.longueur_carre();
			}
	    return ee_ppot;
	}
	float energ_tot(){
	    float energie_tot_av=energie_totale; 
	    float energie_total=0;
	    energie_spins=0;
	    energie_cinetique=0;
	    float energies_degagees=0;
	    if(particules_composees){
		energie_cin_petites=0;
		energie_cin_grosses=0;
	    }
	    if((i_demarre==6||i_demarre==7)&&num_gaz==0){
		energie_cin_premier_type=0;
		energie_cin_second_type=0;
	    }
	    int n_petites_p_av=n_petites_p;
	    n_tot_part=0;
	    if(teilchen[0]!=null){
		//System.out.println(" num_gaz "+num_gaz +" n_particules "+n_particules);
		for (indice=0;indice<n_particules;indice++){
		    //System.out.println(" indice "+indice+" n_particules "+n_particules);
		    if(teilchen[indice].num_particule_mere==-1){
			n_tot_part++;
			teilchen[indice].e_cinetique=teilchen[indice].masse*teilchen[indice].vit.longueur_carre()/2;
			energie_cinetique += teilchen[indice].e_cinetique;
			energie_cin_petites+=teilchen[indice].e_cinetique;
			if((i_demarre==6)||(i_demarre==7)&&num_gaz==0)
			    if(indice<n_particules_premier_type)
				energie_cin_premier_type += teilchen[indice].e_cinetique;
			    else
				energie_cin_second_type += teilchen[indice].e_cinetique;
			if(avec_spin)
			    energie_spins += (float)(double)(Math.pow((double)teilchen[indice].spin,2)/(double)(2*teilchen[indice].moment_d_inertie));
		    }
		}
		if((i_demarre==6||i_demarre==7)&&num_gaz==0){
		    energie_cin_premier_type /= n_particules_premier_type;
		    energie_cin_second_type /= n_particules_second_type;
		}
		n_petites_p=n_tot_part;
		float e_p_compos=energie_cinetique;
		float e1=energie_cinetique;
		if(particules_composees)
		    for (indice=n_particules;indice<n_particules+n_particules_composees;indice++)
			if(teilchen[indice]!=null){
			    n_tot_part++;
			    teilchen[indice].e_cinetique=teilchen[indice].masse*teilchen[indice].vit.longueur_carre()/2;
			    energie_cinetique += teilchen[indice].e_cinetique;
			    energie_cin_grosses+=teilchen[indice].e_cinetique;
			    energies_degagees += energie_degagee;
			}
		n_p_compos=n_tot_part-n_petites_p;
		energie_cinetique_p_compos=energie_cinetique-e_p_compos;
		
		//float energie_avant=energie_totale;
	        energie_total=energie_cinetique+energie_spins;
		energie_cin_par_part=energie_total/n_tot_part;
		if(osc_harm_limite)
		    energie_total-=travaux_elementaires;
		if(osc_harm_limite&&n_event==10){
		    e_au_depart=energie_total;
		    proba_maxw[num_gaz]=calcule_proba_maxwell(energie_cin_par_part);
		}
	    }
	    //if(!(i_demarre==2||i_demarre==4||paroi_mouvante||i_demarre==5||osc_harm_limite)){
	    if(n_event>10&&boqal.paroi.reflechissante&&(!(i_demarre==2||paroi_mouvante))){
		coco=(float)1.002;
		if(osc_harm_limite)
		    coco=(float)1.004;
		if(energie_total>e_au_depart*coco||energie_total<e_au_depart/coco){
		    caca=(float)Math.sqrt(e_au_depart/energie_total);
		    System.out.println("$$ energie_total "+energie_total+" e_au_depart "+e_au_depart+" caca "+caca);
		    for (int i=0;i<n_particules+n_particules_composees;i++)
			if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			    cucu=teilchen[i].vit.longueur();
			    teilchen[i].vit.multiplie_cst(caca);
			    if(avec_spin)
				teilchen[i].vit.multiplie_cst(caca);
			}
		}
	    }
	    return energie_total;
	}
    
	void peint_gaz(){
	    boqal.paroi.peint_paroi();
	    int additifx=0,additify=0;
	    //n_part_petites_energies=0;
	    if(nb_de_gaz==1){
		additifx=boqal.rayon;
		additify=boqal.rayon;
	    }else{
		//additifx=(int)(boqal.x_droite-boqal.x_gauche)/2;	
		additifx=(int)(-boqal.x_gauche);	
		additify=(int)(boqal.hauteur)/2;
		//System.out.println(" xxxxxxxxx num_gaz "+num_gaz+" boqal.x_gauche "+boqal.x_gauche+" boqal.x_droite "+boqal.x_droite);
	    }
	    for (indice=n_particules+n_particules_composees;indice>=0;indice--)
		if(teilchen[indice]!=null)
		    teilchen[indice].peint_particule(additifx,additify);
	    if(boqal.paroi.reflechissante||!circulaire)
		grp_c.drawImage(crop,(int)(centre.x-additifx),(int)(centre.y-additify),null);
	    else
		grp_c.drawImage(crop_chaud,(int)(centre.x-additifx),(int)(centre.y-additify),null);
	    if(geschwind!=null)
		geschwind.peint_vitesses();
	}

	public void verifications_reinitialisations(){
	    a_diffuse=false;
	    somme_des_spins_choc=0;somme_des_mom_orb_choc=0;n_chocs_event=0;
	    float e_totale_av=energie_totale;
	    e_pot=0;travaux_elementaires=travaux_elementaires0;
	    //System.out.println(" niveu 0 energie_totale "+energie_totale);
	    if(avec_spin)
		diff_ecin_lab_cm =0;
	    caca=0;
	    for(int indi=0;indi<n_particules+n_particules_composees;indi++){
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null){
		    caca+=1;
		    bon_secteur=teilchen[indi].calcule_et_verifie_secteur();
		    toto.assigne_facteur(teilchen[indi].vit,teilchen[indi].masse);
		    if(!(i_demarre==7&&num_gaz==0))
			sect_mom.assigne(toto,rayon_moments_2);
		    else
			if(indi<n_particules/2)
			    sect_mom.assigne(toto,ray_moments_2[0]);
			else
			    sect_mom.assigne(toto,ray_moments_2[1]);
		    if(sect_mom.h==8)
			sect_mom.h=7;
		    //if(teilchen[indi].num_partic==10)
		    //	teilchen[indi].sect.print(" bon_secteur "+bon_secteur+" teilchen[indi].sect  ");
		    if(bon_secteur)
			if(sp_pos_mom[0]!=null)
			    sp_pos_mom[0].incremente_comptages(teilchen[indi].rond_interieur_initial,teilchen[indi].sect.h,teilchen[indi].sect.v,num_gaz);
		    if(sp_pos_mom[1]!=null)
			sp_pos_mom[1].incremente_comptages(teilchen[indi].rond_interieur_initial,sect_mom.h,sect_mom.v,num_gaz);
		    teilchen[indi].reinit();
		}
	    }
	    //if(i_demarre==8)
	    //System.out.println(" caca "+caca+" n_tot_part "+n_tot_part);
	    nombre_a_revoir=0;  
	    nombre_acceptes=0;
	}
	public void bouge(){
	    if(!interaction_nulle){
		n_diffus=0;float e_diffus_avant=0,e_diffus_apres=0;
		if(!osc_harm_limite){
		    for(int indi=0;indi<n_particules+n_particules_composees;indi++)
			if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null)
			    teilchen[indi].compare_distances();
		}else{
		    dist_lim_pot2=dist_lim_pot*dist_lim_pot;
		    dist_lim_potmul2=dist_lim_pot*2;
		}
		if(!interaction_nulle)
		    for(int indi=0;indi<n_particules+n_particules_composees;indi++)
			if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null)
			    teilchen[indi].diffusion();
		//System.out.println(" n_diffus "+n_diffus );
	    }
	    for (int indi=n_particules+n_particules_composees-1;indi>=0;indi--)
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null){
		    teilchen[indi].move();
		    if(avec_spin&&teilchen[indi].num_particule_fille1==-1)
			teilchen[indi].theta_spin += teilchen[indi].spin*d_temps/teilchen[indi].rayon;
		}
	    a_reflechi=false;
	    for (int indi=0;indi<n_particules+n_particules_composees;indi++)
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null)
		    teilchen[indi].reflechit_ceux_qui_sont_sortis();
	    
	    if(particules_composees){
		cree_grosse=false;
		if(est_ce_possible(n_p_compos+1,n_petites_p-2,rayon_particule,num_gaz)){
		    
		    for (int indi=0;indi<n_particules;indi++)
			if(teilchen[indi]!=null)
			    teilchen[indi].deja_utilisee=false;
		    for (int indi=0;indi<n_particules;indi++)
			if(teilchen[indi]!=null){
			    if((teilchen[indi].num_particule_mere==-1)&&(teilchen[indi].i_choc==-1)&&(!teilchen[indi].deja_utilisee)){
				if(teilchen[indi].interieur_du_bocal()){
				    k_grosse=-1;
				    teilchen[indi].cree_grosses_particules();
				}
			    }
			}
		}
		elimi_g=false;
		for (int indi=0;indi<n_particules_composees;indi++){
		    int kk=n_particules+indi;
		    if(teilchen[kk]!=null){
			if(!teilchen[kk].ne_pas_defaire){
			    int fille1=teilchen[kk].num_particule_fille1;
			    int fille2=teilchen[kk].num_particule_fille2;
			    if((teilchen[kk].i_choc!=-1||teilchen[kk].reflexion)&&(teilchen[kk].num_particule_fille1!=-1))
				//if((teilchen[kk].i_choc!=-1)&&(teilchen[kk].num_particule_fille1!=-1))
				teilchen[kk].defait_grosses_particules();
			    if(elimination[indi]){
				boolean reff=teilchen[kk].reflexion;
				teilchen[kk]=null;
			    }
			}
		    }
		    if(teilchen[kk]!=null){
			int n1=teilchen[kk].num_particule_fille1;int n2=teilchen[kk].num_particule_fille2;
			toto.assigne(teilchen[n1].posit);toto.soustrait(teilchen[kk].posit);
			float dist1=toto.longueur_carre();
			toto.assigne(teilchen[n2].posit);toto.soustrait(teilchen[kk].posit);
			float dist2=toto.longueur_carre();
			//if((dist1>36.)||(dist2>36.))
			//    System.out.println(" defait n1 "+n1+" n2 "+n2+" kk "+kk+" dist1 "+dist1+ " dist2 "+dist2);  
		    } 
		}
	    }
	    //m_cin=mom_cin_tot();
	    //System.out.println(" apres reflechit grosse m_cin "+m_cin); 
	    if(particules_composees)
		surface_occupee_par_particules=srfc_occupee_par_particules();
	    if(microboules&&rapport_de_surfaces>0.15){
		int n_reculs=0;
		for(int indi=0;indi<n_particules+n_particules_composees;indi++){
		    if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null){
			n_reculs+=teilchen[indi].recule_en_cas_de_recouvrement(true);
	
		    }
		}
	    }
	    if(n_event==5)
		met_les_titres();
	    //System.out.println("222 e_pot "+e_pot);
	}
	float srfc_occupee_par_particules(){
	    if(microboules)
		if(particules_composees)
		    return pi*((n_particules-2*n_particules_composees)*rayon_particule*rayon_particule+n_particules_composees*rayon_grosse_particule*rayon_grosse_particule);
		else if(!(i_demarre==6&&num_gaz==0))
		    return pi*n_particules*rayon_particule*rayon_particule;
		else
		    return pi*n_particules/2*(1+(float)0.25)*(float)Math.pow(rayon_particule,2);
	    else
		return (float)0.;
	}
	void contre_le_deplacement_paroi(){
	    for(int indi=0;indi<n_particules+n_particules_composees;indi++)
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null){
		    if(teilchen[indi].a_diffuse_sur_paroi_mouvante||(num_gaz==1&&teilchen[indi].posit.x<boqal.x_gauche+teilchen[indi].rayon)){
			//teilchen[indi].posit.x += d_pos_paroi_event;
			if(teilchen[indi].num_partic>=n_particules){
			    System.out.println(" grosse particule teilchen[indi].rayon "+teilchen[indi].rayon);
			    teilchen[teilchen[indi].num_particule_fille1].posit.assigne_additionne(teilchen[indi].posit,teilchen[teilchen[indi].num_particule_fille1].distance_a_la_mere);
			    teilchen[teilchen[indi].num_particule_fille2].posit.assigne_additionne(teilchen[indi].posit,teilchen[teilchen[indi].num_particule_fille2].distance_a_la_mere);
			}
		    }
		}
	}
	void verif_suppl(){
	    for(int indi=0;indi<n_particules+n_particules_composees;indi++)
		if(indi<n_particules&&teilchen[indi].num_particule_mere==-1||indi>=n_particules&&teilchen[indi]!=null){
		    if(teilchen[indi].a_diffuse_sur_paroi_mouvante){
			teilchen[indi].posit.x+=d_pos_paroi_event;
			if(num_gaz==0)
			    coco=teilchen[indi].posit.x-(boqal.x_droite-teilchen[indi].rayon);
			else
			    coco=teilchen[indi].posit.x-(boqal.x_gauche+teilchen[indi].rayon);
			//System.out.println("n_event "+n_event+" num_gaz "+num_gaz+" indi "+indi+" d_pos "+d_pos_paroi_event+" coco "+coco);
			//System.out.println(" teilchen[indi].vit.x "+teilchen[indi].vit.x+" vit_paroi "+vit_paroi); 
		    }
		}
	}
	boolean verification( int indi,String strr1,String strr2){
	    boolean ok=true;
	    if((indi<n_particules)&&(teilchen[indi].num_particule_mere!=-1)){
		int moth=teilchen[indi].num_particule_mere;
		toto.assigne(teilchen[indi].posit);toto.soustrait(teilchen[moth].posit);
		float dist_mere_fille=toto.longueur_carre();
		toto.assigne(teilchen[indi].vit);toto.soustrait(teilchen[teilchen[indi].num_particule_mere].vit);
		float dvv=toto.longueur();
		//teilchen[indi].posit.print("***indi "+indi+" teilchen[indi].posit ");
		//teilchen[moth].posit.print("***mere "+teilchen[indi].num_particule_mere+" dist_mere_fille "+dist_mere_fille+" teilchen[moth].posit ");
		if(((float)Math.abs(dist_mere_fille-25.)>0.1)||(dvv>0.1)){
		    ok=false;			    
		    System.out.println(strr1+strr2+indi+" dist_mere_fille "+dist_mere_fille+" dvv "+dvv+" teilchen[indi].num_particule_mere "+teilchen[indi].num_particule_mere+" teilchen[moth].i_choc "+teilchen[moth].i_choc+" teilchen[indi].i_choc "+teilchen[indi].i_choc+" teilchen[moth].reflexion "+teilchen[moth].reflexion);
		    teilchen[indi].posit.print("indi "+indi+" teilchen[indi].posit ");
		    teilchen[moth].posit.print("moth "+moth+" teilchen[moth].posit ");
		}
	    }
	    return ok;
	}
	represente open_represente(String sst,int i){
	    return new represente(sst,i);
	}
	class represente extends fantome{
	    int top=100,left=220,bottom = 520,right = 640;
	    Graphics gTampon_moments,gTampon_moments0;
	    Image crop_moments,crop_moments0;
	    Graphics grp_repr;
	    point centre_vitesses;
	    int  pos_v_x=0,pos_v_y=0;
	    public represente(String s,int numg1){
		super(s,numg1);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    incapaciter[21]=false;
			    num_spectre_a_virer=numg;
			};
		    });		
		
		//setLocation(left,top);
		if(isyst==1){
		    top=400;left=0;bottom = 800;right = 400;
		}
		
		System.out.println("cree represente "+s+" num_gaz "+num_gaz);
		pack();
		setVisible(true);
		setSize(right-left,bottom-top);
		centre_vitesses=new point((float)(right-left)/2,(float)(bottom-top)/2);
		setSize(right+num_gaz*30-(left+num_gaz*30),bottom+num_gaz*30-(top+num_gaz*30));
		setLocation(left+num_gaz*30,top+num_gaz*30);
		grp_repr= getGraphics(); 
		grp_repr.setColor(Color.blue);
		cree_image_moments();
		cree_image_moments0();
	    }
	    void abandon(){
		geschwind.dispose();
		geschwind=null;
		quel_spectre[numg]=null;
	    }
	    void cree_image_moments() {
		crop_moments=createImage(2*rayon_vitesses+20,2*rayon_vitesses+20);
		gTampon_moments=crop_moments.getGraphics();
	    }
	    void cree_image_moments0() {
		crop_moments0=createImage(2*rayon_vitesses+20,2*rayon_vitesses+20);
		gTampon_moments0=crop_moments0.getGraphics();
		gTampon_moments0.setColor(Color.black);
		//gTampon_moments0.drawOval(10,10,2*rayon_vitesses,2*rayon_vitesses);
		System.out.println("surface_moments "+surface_moments);
		//System.out.println(" i "+i+"surf "+surf+" rayon_moments "+rayon_moments);
		//if(i/2*2!=i)
		calcule_rayons_espace_des_p(energie_cin_par_part);
		gTampon_moments0.drawOval(10+rayon_vitesses-rayon_moments,10+rayon_vitesses-rayon_moments,2*rayon_moments,2*rayon_moments);
	    }
	    void peint_vitesses(){
		gTampon_moments.drawImage(crop_moments0,0,0,null);
		for (int i=0;i<n_particules+n_particules_composees;i++){
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			pos_v_x=(int)((float)1.414*teilchen[i].masse*Math.round(teilchen[i].vit.x));
			pos_v_y=(int)((float)1.414*teilchen[i].masse*Math.round(teilchen[i].vit.y));
			if(pos_v_x<rayon_vitesses&&pos_v_x>-rayon_vitesses&&pos_v_y<rayon_vitesses&&pos_v_y>-rayon_vitesses)
			    gTampon_moments.drawImage(crop_part[teilchen[i].icouleur][1],pos_v_x+rayon_vitesses,pos_v_y+rayon_vitesses,null);
		    }
		}
		grp_repr.drawImage(crop_moments,(int)centre_vitesses.x-rayon_vitesses,(int)centre_vitesses.y-rayon_vitesses,null);
	    }	
	}
	abstract class bocal{
	    edge paroi;
	    float energie_cedee=0;
	    point d_impulsion_paroi_mobile;
	    point centre;float hauteur,longueur,hauteur0,longueur0; int rayon0;
	    int rayon=0;
	    int dnb_chocs_contre_paroi=0;
	    float surface=0,perimetre=0;
	    float rayon_secteur_2[][]=new float[2][8];
	    float x_droite=0,x_gauche=0,x_droite_avant=0,x_gauche_avant=0;
	    float surface_libre[]=new float [2];
	    float perimetre_libre[]=new float [2];
	    int i_type_surf=0;	    int longueur_erase=0;
	    secteur sct_bcl,sct_bcl_carre; 
; 


	    bocal(point centre1){
		centre=new point(centre1);
		d_impulsion_paroi_mobile=new point(point_zer0);
		dnb_chocs_contre_paroi=0;
		sct_bcl=new secteur(100,100,8); 
		sct_bcl_carre=new secteur(100,100,16); 
	    }
	    secteur sector_carre(partic pt_n){
		i_type_surf=0;
		if(particules_composees&&pt_n.num_partic>n_particules||i_demarre==6&&num_gaz==0&&pt_n.num_partic>=n_particules_premier_type)
		    i_type_surf=1;
		sct_bcl_carre.assigne(pt_n.posit,x_gauche,x_droite,ryn_prtc[i_type_surf],hauteur);
		return sct_bcl_carre;
	    }
	    secteur sector_carre_print(partic pt_n){
		i_type_surf=0;
		if(particules_composees&&pt_n.num_partic>n_particules||i_demarre==6&&num_gaz==0&&pt_n.num_partic>=n_particules_premier_type)
		    i_type_surf=1;
		sct_bcl_carre.assigne_print(pt_n.posit,x_gauche,x_droite,ryn_prtc[i_type_surf],hauteur);
		return sct_bcl_carre;
	    }
	    abstract secteur sector(partic pt_n);
	    abstract class edge{
		float en_cin0_paroi=(float)2000.;
		//float en_cin0_paroi=(float)500.;
		float masse_element_paroi=(float)3.;
		float energie_particule_recul[]=new float[300]; 
		int nombre_particules_recul[]=new int[300]; 
		float energie_moyenne_recul=0;
		boolean reflechissante=true;
		float en_cin_paroi=en_cin0_paroi;
		float dif_impuls_cumulee=0,dif_impuls_cumulee_avant=0;
		float exp_en_cin=0;
		float vitscal=0;
		float dif_mass=0;
		float s_mass=0;
		float vitesse_element_paroi=0;
		float bbb=0;
		float vitf=0;
		float temperature_minimum=250,temperature_maximum=2000;

		edge(){
		}
		point interac_paroi_isotherme(point unit,float masse,point speed){
		    exp_en_cin=-en_cin_paroi*(float)Math.log((float)Math.random());
		    //exp_en_cin=en_cin_paroi;
		    vitscal=speed.scalaire(unit);
		    dif_mass=boqal.paroi.masse_element_paroi-masse;
		    s_mass=boqal.paroi.masse_element_paroi+masse;
		    vitesse_element_paroi=-(float)Math.sqrt((float)2.*exp_en_cin/masse_element_paroi);
		    toto_int=(int)(exp_en_cin/en_cin_paroi*50);
			if(toto_int>=150)
			    toto_int=149;		  
		    if((float)Math.random()<(float)0.5){
			vitesse_element_paroi=-vitesse_element_paroi;
			toto_int+=150;
		    }
		    vitf=(-dif_mass*vitscal+2*masse_element_paroi*vitesse_element_paroi)/s_mass;
		    if(vitf>0)
		    	vitf=-vitf;
		    boqal.paroi.energie_particule_recul[toto_int]+=masse*vitf*vitf/2;
		    boqal.paroi.nombre_particules_recul[toto_int]++;
		    toto.assigne_facteur(unit,vitf-vitscal);
		    //speed.print(" vitesse_element_paroi "+vitesse_element_paroi+" vitscal "+vitscal+" vitf "+vitf+" speed ");
		    //toto.print(" vitf "+vitf+" vitscal  "+vitscal+" toto(dfvt) ");
		    return toto;
		}
		abstract void peint_paroi();
	    }
      	    abstract void surf_perim();
	    abstract void surf_perim_instant();
	    abstract boolean est_dedans(point pp, float rayo_part);
	    abstract void surf_perim_libre(int type_surface, int rayn);
	}
	class bocal_circulaire extends bocal{
	    int ray;
	    //edge paroi;
	    bocal_circulaire(point centre, int diametre_bocal1){
		super(centre);
		rayon0=diametre_bocal1/2;
		dist_car_max=rayon0*rayon0/2;
		longueur=diametre_bocal1;
		hauteur=diametre_bocal1;
		x_gauche=-hauteur/2;
		x_droite=hauteur/2;
		ray=(int)rayon0;
		surf_perim();
		paroi=new edge();
		System.out.println("paroi "+paroi+" ray "+ray);
	    }
	    boolean est_dedans(point pp, float rayo_part){
		//pp.print(" ray "+ray+" rayo_part "+rayo_part+" pp ");
		return (pp.longueur_carre()<(ray-rayo_part)*(ray-rayo_part));
	    }
	    public secteur sector(partic pt_n){
		i_type_surf=0;
		if(particules_composees&&pt_n.num_partic>=n_particules||i_demarre==6&&num_gaz==0&&pt_n.num_partic>=n_particules_premier_type)
		    //if(i_demarre==6&&num_gaz==0&&pt_n.num_partic>=n_particules_premier_type)
		    i_type_surf=1;
		if(nb_de_types_de_surf_part==1&&i_type_surf==1){
		    System.out.println(" nb_de_types_de_surf_part "+nb_de_types_de_surf_part+" i_type_surf "+i_type_surf);
		    rayon_secteur_2[i_type_surf][100]=0; 
		}
		sct_bcl.assigne(pt_n.posit,rayon_secteur_2[i_type_surf]);
		
		/*
		if(pt_n.num_partic<n_particules){
		    
		    sct_bcl.print(" i_type_surf "+i_type_surf+" sct_bcl ");
		    pt_n.posit.print("pt_n.num_partic "+pt_n.num_partic+" pt_n.posit ");
		}
		*/
		return sct_bcl;
		// ces deux dernières lignes donnent des résultats très bizarres!A ne pas faire!
	
		
		//return new secteur(pt_n.posit,rayon_secteur_2[i_type_surf],8);
	    }
	    void surf_perim(){
		rayon=rayon0;
		surface=pi*rayon*rayon;
		perimetre=(float)2.*pi*(rayon);
	    }
	    void surf_perim_instant(){
	    }
	    void surf_perim_libre(int type_surface, int rayy_part){
		rayon=rayon0;		
		surface_libre[type_surface]=pi*(rayon-rayy_part)*(rayon-rayy_part);
		perimetre_libre[type_surface]=(float)2.*pi*(rayy_part);		
		float surf=0;
		for(int i=0;i<=7;i++){
		    surf+=surface_libre[type_surface]/8.;
		    rayon_secteur_2[type_surface][i]=surf/pi;
		    float rrr=(float)Math.sqrt(rayon_secteur_2[type_surface][i]);
		    System.out.println("kkkkkkkkkkkkk type_surface "+type_surface+" i "+i+" rrr "+rrr+" rayon_secteur_2[type_surface][i] "+rayon_secteur_2[type_surface][i]+" (rayon-rayy_part)*(r.ayon-rayy_part) "+(rayon-rayy_part)*(rayon-rayy_part));		    
		}
	    }
	    class edge extends bocal.edge{
		edge(){
		    super();
		}
		void peint_paroi(){
		    if(boqal.paroi.reflechissante)
			gTampon.drawImage(crop0,0,0,null);

		    else
			gTampon_chaud.drawImage(crop_chaud0,0,0,null);
		}
	    }
	}
	class bocal_rectangulaire extends  bocal{
	    bocal_rectangulaire(point centre1, float longueur1,float hauteur1){
		super(centre1);
		longueur0=longueur1;
		hauteur0=hauteur1;
		surf_perim();
		paroi=new edge();
	    }
	    void surf_perim(){
		longueur=longueur0;
		hauteur=hauteur0;
		surface=longueur*hauteur;
		perimetre=(float)2.*(longueur+hauteur);
		x_droite=longueur/(float)2.;
		x_gauche=-x_droite;
	    }
	    void surf_perim_instant(){
		surface=hauteur*(x_droite-x_gauche);
		perimetre=(float)2.*(x_droite-x_gauche+hauteur);	
		surface_libre[0]=(x_droite-x_gauche-2*ryn_prtc[0])*(hauteur-2*ryn_prtc[0]);
		perimetre_libre[0]=(float)2.*(x_droite-x_gauche+hauteur-4*ryn_prtc[0]);
		if(nb_de_types_de_surf_part==2){
		    surface_libre[1]=(x_droite-x_gauche-2*ryn_prtc[1])*(hauteur-2*ryn_prtc[1]);
		    perimetre_libre[1]=(float)2.*(x_droite-x_gauche+hauteur-4*ryn_prtc[1]);
		}
	    }
	    void surf_perim_libre(int type_surface, int rayy){
		surface_libre[type_surface]=(x_droite-x_gauche-2*rayy)*(hauteur-2*rayy);
		perimetre_libre[type_surface]=(float)2.*(x_droite-x_gauche+hauteur-4*rayy);
	    }
	    secteur sector(partic pt_n){
		i_type_surf=0;
		if(particules_composees&&pt_n.num_partic>n_particules||i_demarre==6&&num_gaz==0&&pt_n.num_partic>=n_particules_premier_type)
		    i_type_surf=1;
		sct_bcl.assigne(pt_n.posit,x_gauche,x_droite,ryn_prtc[i_type_surf],hauteur);
		return sct_bcl;
	    }
	    boolean est_dedans(point pp, float rayo){
		return (pp.x<=x_droite-rayo&&pp.x>=x_gauche+rayo&&pp.y<=hauteur/2-rayo&&pp.y>=-hauteur/2+rayo);
	    }
	    class edge extends bocal.edge{
		int pos_dr=0, pos_ht=0, pos_bs=0;
		edge(){
		    super();
		}
		void peint_paroi(){
		    //if(num_gaz==0)
		    //gTampon.drawImage(crop,0,0,null);
			//appelant.eraserect(grp_c,(int)(centre.y-hauteur/2),(int)(centre.x+boqal.x_gauche),(int)(centre.y+hauteur/2),(int)(gas[1].centre.x+gas[1].boqal.x_droite),Color.white);
		    longueur_erase=(int)(x_droite-x_gauche);
		    if(num_gaz==1){
			if(gas[0].boqal.longueur_erase>longueur_erase)
			    longueur_erase=gas[0].boqal.longueur_erase;
			longueur_erase+=2;
		    }	
		    appelant.eraserect(gTampon,0,0,(int)hauteur+2,longueur_erase,Color.white);
		    if(boqal.paroi.reflechissante)
			gTampon.setColor(Color.black);
		    else
			gTampon.setColor(Color.red);
		    gTampon.drawRect(0,0,(int)(x_droite-x_gauche),(int)hauteur);
		}
	    }
	}
	abstract class partic{
	    float masse;float spin=0;boolean ne_pas_defaire=false,slc_pr=false;
	    int posx_min=0,posx_max=0;point distance_a_la_mere;point d_posit,vit_dt;
	    boolean a_deja_recule=false,vas_incr=false,deja_coco=false;
	    point position_juste_apres_choc;
	    int j_p_v=0;
	    float dif_energie_dans_choc=0;float e_cinetique=0;
	    point force,force_prec;secteur sect,sect_prec,sect_carre,sect_carre_prec;boolean mauvais_secteur=false;
	    objets_sauves obj_salv;objets_sauves obj_safe[]=new objets_sauves[4];
	    boolean rond_interieur_initial=false;
	    boolean reflexion=false,a_deja_reflechi=false;float surface_atteignable=0,perimetre_atteignable=0;
	    float distance=0;
     	    int nb_part_classees=0;
	    boolean a_diffuse_sur_paroi_mouvante=false;
	    float moment_d_inertie=0;
	    int i_choc=-1,i_choc_prec=-1;float long_vit=0;
	    int num_partic;boolean deja_utilisee; 
	    point posit,vit,impulsion,posit_prec,vit_prec;
	    point new_vitesse,new_position;
	    float d_depuis_dernier_choc=0;
 	    int icouleur;float rayon;int rayon_entier,index_rayon_entier=2;
	    int posx=0,posy=0;	    float theta_spin=0; float e_spin=0;
	    int num_particule_mere=-1,num_particule_soeur=-1,num_particule_fille1=-1,num_particule_fille2=-1;
	    partic(point posit1,float mass,int rr,point vit1,int icouleur1,int nu_b){
		masse=mass;rayon=rr;
		rayon_entier=rr;
		index_rayon_entier=rayon_entier-1;
		num_partic=nu_b;
		force=new point(point_zer0);
		force_prec=new point(point_zer0);
		new_vitesse=new point(point_zer0);
		new_position=new point(point_zer0);
		vit_dt=new point(point_zer0);
		d_posit=new point(point_zer0);
		position_juste_apres_choc=new point(point_zer0);
		obj_salv=new objets_sauves();
		for (int j=0;j<4;j++)
		    obj_safe[j]=new objets_sauves();
		distance_a_la_mere=new point(point_zer0);
		sect=new secteur(100,100,8);
		sect_prec=new secteur(100,100,8);
		sect_carre=new secteur(100,100,16);
		sect_carre_prec=new secteur(100,100,16);
		deja_utilisee=false;
		icouleur=icouleur1;
		posit=new point(posit1);posit_prec=new point(posit1);
		vit=new point(vit1);vit_prec=new point(vit1);
		impulsion=new point(masse*vit.x,masse*vit.y);
		
		spin=0;
	    }
	    void peint_particule(int additifx,int additify){
		posx=(int)(additifx+posit.x+(float)0.5)-rayon_entier;
		posy=(int)(additify+posit.y+(float)0.5)-rayon_entier;
		if(!(nb_de_gaz==2&&posit.y>=boqal.hauteur/2-rayon_entier)){
		    if(boqal.paroi.reflechissante||!circulaire){
			if(!machina_vapore)
			    gTampon.drawImage(crop_part[icouleur][index_rayon_entier],posx,posy,null);
			else
			    gTampon.drawImage(crop_part[15][index_rayon_entier],posx,posy,null);
			if(avec_spin)
			    dessine_point_spin(gTampon);
		    }else{
			gTampon_chaud.drawImage(crop_part[icouleur][index_rayon_entier],posx,posy,null);
			if(avec_spin)
			    dessine_point_spin(gTampon_chaud);
		    }
		}
	    }
	    void dessine_point_spin(Graphics gph){
		gph.setColor(Color.black);
		int x_init=(int)Math.round(posx+rayon+rayon*(float)Math.cos(theta_spin));
		int y_init=(int)Math.round(posy+rayon+rayon*(float)Math.sin(theta_spin));
		gph.drawLine(x_init,y_init,x_init-1,y_init);
		gph.drawLine(x_init,y_init-1,x_init-1,y_init-1);
	    }
	    void reinit(){
		reflexion=false;
		i_choc_prec=i_choc;
		i_choc=-1;
		if(microboules){
		    a_deja_recule=false;
		    dif_energie_dans_choc=0;
		    nb_part_classees=0;
		    for (int j=0;j<4;j++)
			obj_safe[j].nm_part=-1;
		}
		force_prec.assigne(force);
		posit_prec.assigne(posit);
		force.assigne(point_zer0);
		vit_prec.assigne(vit);
		if(num_partic>=n_particules)
		    ne_pas_defaire=false;
		if(paroi_mouvante)
		    a_diffuse_sur_paroi_mouvante=false;
	    }

	    int recule_en_cas_de_recouvrement(boolean reculez){
		int recul=0;
		for (int i=num_partic+1;i<n_particules+n_particules_composees;i++) 
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
			if(i!=num_partic&&!teilchen[i].a_deja_recule)
			    if(sect.h-teilchen[i].sect.h<=1&&sect.h-teilchen[i].sect.h>=-1||paroi_mouvante&&sect.h-teilchen[i].sect.h<=2&&sect.h-teilchen[i].sect.h>=-2)
				if(sect.v-teilchen[i].sect.v<=1&&sect.v-teilchen[i].sect.v>=-1||circulaire&&(sect.v-teilchen[i].sect.v==7||sect.v-teilchen[i].sect.v==-7)){
				    dist_pos.assigne_soustrait(posit,teilchen[i].posit);
				    somme_ray=rayon+teilchen[i].rayon;
				    if(dist_pos.longueur_carre()<somme_ray*somme_ray){
					recul=1;
					if(reculez){
					    float ttl=dist_pos.longueur();
					    if(ttl>0.1){
						float fac=(somme_ray+(float)0.1-ttl)/ttl;
						toto.assigne(posit);
						posit.additionne_facteur(dist_pos,fac);
						if(!interieur_du_bocal())
						    posit.assigne(toto);
						else
						    a_deja_recule=true;
						toto.assigne(teilchen[i].posit);
						teilchen[i].posit.additionne_facteur(dist_pos,-fac);
						if(!teilchen[i].interieur_du_bocal())
						    teilchen[i].posit.assigne(toto);
						else
						    teilchen[i].a_deja_recule=true;
						//System.out.println(" ttl "+ttl+" dist_pos.longueur_carre() "+dist_pos.longueur_carre()+" num_partic "+num_partic);
						
					    }
					}
					break;
				    }
				}
		return recul;
	    }
	    boolean calcule_et_verifie_secteur(){
		sect_prec.assigne(sect); 
		sect.assigne(boqal.sector(this));
		sect_carre_prec.assigne(sect_carre); 
	 	sect_carre.assigne(boqal.sector_carre(this));
		mauvais_secteur=sect.h>=sect.nb_secteurs||sect.v>=sect.nb_secteurs||sect.h<0||sect.v<0;
		if(mauvais_secteur){
		    		    remise_a_l_interieur_du_bocal(false);
		    sect.assigne(boqal.sector(this));
		    sect_carre.assigne(boqal.sector_carre(this));
		    //if(interaction_nulle&&!reflexion)
		    //posit.print(" ùùùùùùùùùsect.h "+sect.h+" sect.v "+sect.v+" i_choc "+i_choc+" posit");
		    mauvais_secteur=sect.h>=sect.nb_secteurs||sect.v>=sect.nb_secteurs||sect.h<0||sect.v<0;
		    if(mauvais_secteur){
			System.out.println("num_partic "+num_partic+" rayon "+rayon);
			posit.print(" vvvvvsect.h "+sect.h+" sect.v "+sect.v+" i_choc "+i_choc+" posit");
			if(isyst==0)
			    teilchen[-51]=null;
			remise_a_l_interieur_du_bocal(true);
			if(interaction_nulle&&!reflexion)
			    posit.print(" ùùùùùùùùùsect.h "+sect.h+" sect.v "+sect.v+" i_choc "+i_choc+" posit");
			//teilchen[-51]=null;
			mauvais_secteur=sect.h>=sect.nb_secteurs||sect.v>=sect.nb_secteurs||sect.h<0||sect.v<0;
			if(mauvais_secteur){
			    posit.print(" ****num_partic "+num_partic+" sect.h "+sect.h+" sect.v "+sect.v+" reflexion "+reflexion+" i_choc "+i_choc+" posit");
			    vit.print(" vit ");
			    posit_prec.print(" sect_prec.h "+sect_prec.h+" sect_prec.v "+sect_prec.v+" posit_prec ");
			    vit_prec.print(" vit_prec ");
			    teilchen[-50]=null;
			}
		    }
		}
		if(posit.y>boqal.hauteur/2+5){
		    sect.print("ggggg num_gaz "+num_gaz+" posx "+posx+" posy "+posy+" num_partic "+num_partic+" sect ");
		    posit.print(" ggggg n_event "+n_event+" posit ");
		}
		return !mauvais_secteur;
	    }
	    boolean interieur_du_bocal(){
		if(circulaire)
		    if(sect.h<sect.nb_secteurs-1)
			return true;
		    else
			return (posit.longueur_carre()<=(float)Math.pow(boqal.rayon-rayon,2));
		else{
		    coco=boqal.hauteur/2-rayon;
		    return ((posit.x<=boqal.x_droite-rayon)&&(posit.x>=boqal.x_gauche+rayon)&&(posit.y<=coco)&&(posit.y>=-coco));
		}
	    }
	    void remise_a_l_interieur_du_bocal(boolean imprime){
		if(circulaire){
		    if(imprime)System.out.println("on remet dans le bocal posit.longueur() "+posit.longueur()+" num_partic "+num_partic+" i_choc "+i_choc+" reflexion "+reflexion);
		    if(posit.longueur_carre()>(float)Math.pow(boqal.rayon-rayon,2)){
			float pl=posit.longueur();
			float ps=posit.scalaire(vit)/pl;
			toto.assigne_diviseur(posit,pl);toto.multiplie_cst(2*ps);
			vit.soustrait(toto);
			posit.multiplie_cst((boqal.rayon-rayon-(float)0.5)/pl);	
			if(imprime)System.out.println("on a remis dans le bocal posit.longueur() "+posit.longueur()+" i_choc "+i_choc+" reflexion "+reflexion);
		    }
		}else{
		    if(imprime)
			System.out.println("on remet dans le bocal posit.x "+posit.x+" posit.y "+posit.y+" boqal.x_droite "+boqal.x_droite+" boqal.x_gauche "+boqal.x_gauche+" i_choc "+i_choc+" reflexion "+reflexion);
		    float h=boqal.hauteur/2-rayon;
		    if(posit.x>=boqal.x_droite-rayon){
			posit.x=boqal.x_droite-rayon-(float)0.1;
			vit.x=-(float)Math.abs(vit.x);
		    }
		    if(posit.x<=boqal.x_gauche+rayon){
			posit.x=boqal.x_gauche+rayon+(float)0.1;
			vit.x=(float)Math.abs(vit.x);
		    }
		    if(posit.y>h){
			posit.y=h-(float)0.1;
			vit.y=-(float)Math.abs(vit.y);
		    }	
		    if(posit.y<-h){
			posit.y=-h+(float)0.1;
			vit.y=(float)Math.abs(vit.y);
		    }
		    if(imprime)
			vit.print("on a remis dans le bocal posit.x "+posit.x+" posit.y "+posit.y+" vit ");
		}
	    }
	    public void reflechit_ceux_qui_sont_sortis(){
		//System.out.println("num_particule_mere "+num_particule_mere+" num_partic "+num_partic);
		if(circulaire){
		    if(sect.h>=sect.nb_secteurs-1){
			//posit.print(" sect.h "+sect.h+" posit ");
			if(posit.scalaire(vit)>0){
			    rayon_fiduciel=boqal.rayon-rayon;
			    if (posit.longueur_carre()>rayon_fiduciel*rayon_fiduciel){ 
				//System.out.println(" posit.scalaire(vit)"+posit.scalaire(vit));
				toto.assigne(posit);
				a_trinome=(double)vit.longueur_carre();
				if(Math.abs(vit.y)>Math.abs(vit.x)){
				    //dist_au_cercle.assigne(trouve_solution_y());
				    bp_trinome=-((double)toto.x*(double)vit.x+(double)vit.y*(double)toto.y)*(double)vit.y;
				    c_trinome=((double)toto.longueur_carre()-(double)(rayon_fiduciel*rayon_fiduciel))*(double)vit.y*(double)vit.y;
				}else{
				    //dist_au_cercle.assigne(trouve_solution_x());
				    bp_trinome=-((double)toto.y*(double)vit.y+(double)vit.x*(double)toto.x)*(double)vit.x;
				    c_trinome=((double)toto.longueur_carre()-(double)(rayon_fiduciel*rayon_fiduciel))*(double)vit.x*(double)vit.x;
				}
				delta_trinome=bp_trinome*bp_trinome-a_trinome*c_trinome;
				if(delta_trinome>0){
				    sqrtdelta=Math.sqrt(delta_trinome);
				    if(Math.abs(vit.y)>Math.abs(vit.x)){
					
					tutu.y=(float)((-bp_trinome+sqrtdelta)/a_trinome);
					tutu.x=tutu.y*vit.x/vit.y;
					tata.y=(float)((-bp_trinome-sqrtdelta)/a_trinome);
					tata.x=tata.y*vit.x/vit.y;
				    }else{
					tutu.x=(float)((-bp_trinome+sqrtdelta)/a_trinome);
					tutu.y=tutu.x*vit.y/vit.x;
					tata.x=(float)((-bp_trinome-sqrtdelta)/a_trinome);
					tata.y=tata.x*vit.y/vit.x;
				    }
				    if(tata.longueur_carre()<tutu.longueur_carre()){
					dist_au_cercle.assigne(tata);
				    }else{
					dist_au_cercle.assigne(tutu);
				    }
				}else
				  dist_au_cercle.assigne(point_zer0);  
				posit_au_cercle.assigne_soustrait(posit,dist_au_cercle);
				//posit_au_cercle.print(" posit_au_cercle ");
				unite.assigne_diviseur(posit_au_cercle,rayon_fiduciel);
				unite_transverse.x=-unite.y;unite_transverse.y=unite.x;
				toto.assigne(dist_au_cercle);
				toto.symetrique(unite_transverse);
				posit.assigne_additionne(posit_au_cercle,toto);

				if(num_partic>=n_particules)
				    a_reflechi=true;
				if(boqal.paroi.reflechissante){
				    //float energie_avant=(float)0.5*(masse*vit.longueur_carre());
				    //System.out.println(" vivit ");
				    boqal.paroi.dif_impuls_cumulee+=((float)2.*masse*vit.scalaire(unite));
				    vit.additionne_facteur(unite,-2*vit.scalaire(unite));
				    /*
				      float energie_apres=(float)0.5*(masse*vit.longueur_carre());
				      float dif_e=(float)Math.abs(energie_apres-energie_avant);
				      float s_e=energie_apres+energie_avant;
				      if(dif_e/s_e >0.0001)
				      System.out.println("reflexion num_partic "+num_partic+"  energie_avant "+ energie_avant+ " energie_apres "+energie_apres);
				    */
				    // System.out.println("dif_impuls_cumulee "+boqal.paroi.dif_impuls_cumulee);
				}else{
				    dfvt=boqal.paroi.interac_paroi_isotherme(unite,masse,vit);
				    //dfvt.assigne_facteur(unite,-2*vit.scalaire(unite));
				    boqal.paroi.dif_impuls_cumulee-=(masse*dfvt.scalaire(unite));
				    atf.assigne(vit);
				    vit.additionne(dfvt);
				    longueur_v=vit.longueur();			
				    manque *=longueur_v/atf.longueur();
				}
				//d_posit.additionne_facteur(vit,manque/longueur_v);
				//posit.additionne(d_posit);
				reflexion=true;
				if(num_partic>=n_particules)
				    a_reflechi=true;
			    }
			}
		    }
		}else{
		    if((sect_carre.v==sect_carre.nb_secteurs-1||sect_carre.v==0)&&Math.abs(vit.y)>(float)0.001){
			if ((float)Math.abs(posit.y)>=boqal.hauteur/2-rayon){
			    if(posit.y*vit.y<0){
				    vit.y=-vit.y;//et elle sera inversée tout de suite apres.
			    }
			}
				
			if (((float)Math.abs(posit.y)>=boqal.hauteur/2-rayon)&&(posit.y*vit.y>0)){
			    longueur_v=vit.longueur();
			    yyy=boqal.hauteur/2-rayon;
			    if(posit.y<0.)
				yyy=-yyy;
			    coco=(posit.y-yyy);
			    posit.y-=2*(posit.y-yyy);
			    reflexion=true;
			    if(num_partic>=n_particules)
				a_reflechi=true;
			    if(boqal.paroi.reflechissante){			
				//posit.y -= (float)2.*(posit.y-yyy);			    
				vit.y=-vit.y;
				boqal.paroi.dif_impuls_cumulee += ((float)2.*masse*(float)Math.abs(vit.y));
				//vit.print("num_partic "+num_partic+" sur y vit apres ");
			    }else{
				if(yyy>0)
				    unite.assigne((float)0.,(float)1.);
				else
				    unite.assigne((float)0.,(float)-1.);
				dfvt=boqal.paroi.interac_paroi_isotherme(unite,masse,vit);
				//dfvt.assigne((float)0.,-2*vit.y);
				boqal.paroi.dif_impuls_cumulee-=(masse*dfvt.scalaire(unite));
				atf.assigne(vit);
				vit.additionne(dfvt);
				if(vit.scalaire(unite)>0){
				    //vit.print("yyyyyyyvit.scalaire(unite) "+vit.scalaire(unite)+" vit ");
				    vit.soustrait_facteur(unite,(float)2.*vit.scalaire(unite));
				}
			    }
			    
			}
		    }
		    if((sect_carre.h==sect_carre.nb_secteurs-1||sect_carre.h==0)&&Math.abs(vit.x-vit_paroi)>(float)0.001){
			//vviitt_paroi=0;
			//d_pos_paroi_event=0;
			//if(paroi_mouvante&&(sect_carre.h==sect_carre.nb_secteurs-1&&num_gaz==0||sect_carre.h==0&&num_gaz==1)){
			    vviitt_paroi=vit_paroi;
			    //}
			vas_y=false;
			temps_collision=1000;
			if(num_gaz==0){
			    //if(sect_carre.h==sect_carre.nb_secteurs-1&&vit.x-vviitt_paroi>0||sect_carre.h==0&&vit.x-vviitt_paroi<0){
			    if(sect_carre.h==sect_carre.nb_secteurs-1&&vit.x>0||sect_carre.h==0&&vit.x<0){
				temps_collision=(posit.x-(boqal.x_droite+d_pos_paroi_event+rayon))/(vviitt_paroi-vit.x);
				//if(posit.x-(boqal.x_droite-rayon)>0&&temps_collision<0)
				coco=posit.x-(boqal.x_droite+d_pos_paroi_event-rayon);
				if(coco>0)
				    vas_y=true;
				if (coco>-Math.abs(d_pos_paroi_event)){
				    numero_a_revoir[nombre_a_revoir]=num_partic;
				    posit_avant[nombre_a_revoir].assigne(posit);
				    vit_avant[nombre_a_revoir].assigne(vit);
				    if(nombre_a_revoir<99){
					nombre_a_revoir++;
					nombre_a_revoir_cumule++;
				    }

				}
			    }
			}else{
			    //if(sect_carre.h==0&&vit.x-vviitt_paroi<0||sect_carre.h==sect_carre.nb_secteurs-1&&vit.x>0){
			    if(sect_carre.h==0&&vit.x<0||sect_carre.h==sect_carre.nb_secteurs-1&&vit.x>0){
				temps_collision=(posit.x-(boqal.x_gauche+d_pos_paroi_event+rayon))/(vviitt_paroi-vit.x);
				coco=posit.x-(boqal.x_gauche+d_pos_paroi_event+rayon);
				
				if(coco<0)
				    vas_y=true;
				if(coco<Math.abs(d_pos_paroi_event)){
				    numero_a_revoir[nombre_a_revoir]=num_partic;
				    posit_avant[nombre_a_revoir].assigne(posit);
				    vit_avant[nombre_a_revoir].assigne(vit);
				    if(nombre_a_revoir<99){
					nombre_a_revoir++;
					nombre_a_revoir_cumule++;
				    }
				}
			    }
			}
			if(deja_coco){
			    System.out.println("deja_cocodeja_cocodeja_cocodeja_coco n_event "+n_event+" num_partic "+num_partic+" vas_y "+vas_y);
			    sect_carre.print("deja_coco i_choc "+i_choc+" posit.x "+posit.x+" vit.x "+vit.x+" temps_collision "+temps_collision+" vit_paroi "+vit_paroi+" boqal.x_gauche "+boqal.x_gauche+" boqal.x_droite "+boqal.x_droite+" sect_carre ");
			}
			if (sect.h==sect.nb_secteurs-1&&posit.x>=boqal.x_droite+d_pos_paroi_event-rayon||sect.h==0&&posit.x<=boqal.x_gauche+d_pos_paroi_event+rayon){
			    if(sect.h==0&&vit.x>0||sect.h==sect.nb_secteurs-1&&vit.x<0){
				//if((float)Math.abs(position_juste_apres_choc.x)>(float)Math.abs(posit.x)||osc_harm_limite)
				    vit.x=-vit.x;//et elle sera inversée tout de suite apres.
			    }
			}
			if(vas_y)
			    fais_la_reflexion_xxx();
		    }
		}
		termine_reflechit();
	    }
	    void termine_reflechit(){
		if(reflexion){
		    boqal.dnb_chocs_contre_paroi++;
		    boqal.energie_cedee += (masse*(vit.longueur_carre()-vit_prec.longueur_carre())/(float)2.);
		    if(num_particule_fille1!=-1){
			teilchen[num_particule_fille1].posit.assigne(posit);
			teilchen[num_particule_fille1].posit.additionne(teilchen[num_particule_fille1].distance_a_la_mere);
			teilchen[num_particule_fille2].posit.assigne(posit);
			teilchen[num_particule_fille2].posit.additionne(teilchen[num_particule_fille2].distance_a_la_mere);
			teilchen[num_particule_fille1].vit.assigne(vit);
			teilchen[num_particule_fille2].vit.assigne(vit);
		    }
		    
		}
	    }
	    void fais_la_reflexion_xxx(){
		if(sect_carre.h==sect_carre.nb_secteurs-1)
		    xxx=boqal.x_droite+d_pos_paroi_event-rayon;
		else if(sect_carre.h==0)
		    xxx=boqal.x_gauche+d_pos_paroi_event+rayon;
		/*
		if(second_passage){
		    posit.print(" num_gaz "+num_gaz+" num_partic "+num_partic+" xxx "+xxx+" posit ");
		    vit.print(" vviitt_paroi "+vviitt_paroi+" vit ");
		    }
		*/
		//if(reflexion)
		titi.assigne(posit);
		tata.assigne(vit);

		//if (posit.x>=xxx&&vit.x-vviitt_paroi>0&&sect_carre.h==sect_carre.nb_secteurs-1||posit.x<=xxx&&vit.x-vviitt_paroi<0&&sect_carre.h==0){
		if (posit.x>=xxx&&(vit.x-vviitt_paroi>0||i_choc!=-1)&&sect_carre.h==sect_carre.nb_secteurs-1||posit.x<=xxx&&(vit.x-vviitt_paroi<0||i_choc!=-1)&&sect_carre.h==0){
		    posit.x-=2*(posit.x-xxx);
		    reflexion=true;
		    if(num_partic>=n_particules)
			a_reflechi=true;
		    a_diffuse_sur_paroi_mouvante=paroi_mouvante&&reflexion&&(num_gaz==0&&sect.h==sect.nb_secteurs-1||num_gaz==1&&sect.h==0);
		    if(second_passage){
			nombre_acceptes_second_pass++;
			nombre_acceptes_second_pass_cumule++;
		    }else{
			nombre_acceptes++;
			nombre_acceptes_cumule++;
		    }
		    if(deja_coco)
			System.out.println("refrefref n_event "+n_event+" num_gaz "+num_gaz+" num_partic "+num_partic);
		    /*
		    if(second_passage)
			System.out.println(" n_event "+n_event+" num_gaz "+num_gaz+" num_partic "+num_partic);
		    */
		    if(boqal.paroi.reflechissante){
			float vitx=vit.x;				
			if(a_diffuse_sur_paroi_mouvante){
			    float S=masse+masse_paroi_mouvante;
			    float dif_som=(masse_paroi_mouvante-masse)/S;
			    vit.x=-vitx*dif_som+vviitt_paroi*2*masse_paroi_mouvante/S;
			    //float dmv=masse_paroi_mouvante/S*2*masse*(vitx-vit_paroi) ;
			    float dmv=masse*(vitx-vit.x) ;
			    d_momx_part_event-=dmv;
			    d_energie_x_part_event+=masse*(vit.x*vit.x-vitx*vitx)/2;
			    if(!second_passage){
				energie_x_part_event+=masse*vit.x*vit.x/2;
				energie_x_part_avant+=masse*vitx*vitx/2;
			    }else
				energie_x_part_event_sec_pass+=masse*vit.x*vit.x/2;
			    cucu=vviitt_paroi;
			    vviitt_paroi=vit.x+vitx-cucu;
			    vit_paroi=vviitt_paroi;
			    boqal.d_impulsion_paroi_mobile.x += dmv;
			    boqal.paroi.dif_impuls_cumulee += (float)Math.abs(dmv);
			}else{
			    boqal.paroi.dif_impuls_cumulee += (float)2.*(float)Math.abs(masse*vitx);
			    vit.x=-vit.x;
			    //vit.print("num_partic "+num_partic+"vitx "+vitx+" vit apres ");
			}
		    }else{
			float xun=1;
			if((float)Math.abs(posit.x-(boqal.x_droite-rayon))>(float)Math.abs(posit.x-(boqal.x_gauche+rayon)))
			    xun=-1;
			unite.assigne(xun,(float)0.);
			atf.assigne(vit);
			vit.x -=vit_paroi;
			dfvt=boqal.paroi.interac_paroi_isotherme(unite,masse,vit);
			//dfvt.assigne(-2*vit.x,(float)0.);
			boqal.paroi.dif_impuls_cumulee-=(masse*dfvt.scalaire(unite));
			//boqal.paroi.dif_impuls_cumulee+=boqal.paroi.masse_element_paroi*(boqal.paroi.vitesse_element_paroi_finale-boqal.paroi.vitesse_element_paroi);
			vit.additionne(dfvt);
			vit.x +=vit_paroi;
			if(vit.scalaire(unite)>0)
			    vit.soustrait_facteur(unite,(float)2.*vit.scalaire(unite));
			if(a_diffuse_sur_paroi_mouvante)
			    boqal.d_impulsion_paroi_mobile.additionne_facteur(dfvt,-masse);			
		    }
		}
		totologic=posit.x>boqal.x_gauche+d_pos_paroi_event+rayon&&posit.x<boqal.x_droite+d_pos_paroi_event-rayon&&posit.y<boqal.hauteur/2-rayon&&posit.y>-boqal.hauteur/2+rayon;
		if(isyst==0&&!totologic){
		    //if(!((posit.x>boqal.x_droite-rayon)||(posit.x<boqal.x_gauche+rayon))){
		    posit.print(" num_gaz "+num_gaz+" num_partic "+num_partic+" xxx "+xxx+" boqal.x_gauche "+boqal.x_gauche+" boqal.x_droite "+boqal.x_droite+" yyy "+yyy+" reflexion "+reflexion+" posit " );
		    vit.print("n_event "+n_event+"i_choc "+i_choc+" vit ");
		    titi.print(" +d_pos_paroi_event "+d_pos_paroi_event+" titi ");
		    tata.print(" tata ");
		    posit_prec.print(" posit_prec ");
		    vit_prec.print(" vit_prec ");
		}
	    }
	    abstract void  move();
	    abstract void compare_distances();
	    abstract void  diffusion();
	    abstract void cree_grosses_particules();
	    abstract void defait_grosses_particules();
	    abstract boolean  selec_paire(partic pt_i);
	    abstract boolean  selec_paire_bis(partic pt_i);
	}
	class particule extends partic{
	    particule(point posit1,float mass,int ray,point vit1, int icouleur1,int nu_b){
		super(posit1,mass,ray,vit1,icouleur1,nu_b);
	    }
	    boolean  selec_paire(partic pt_i){
		return true;
	    }
	    boolean  selec_paire_bis(partic pt_i){
		return true;
	    }
	    void cree_grosses_particules(){
	    }
	    void compare_distances(){
	    }
	    void defait_grosses_particules(){
	    }
	    void diffusion(){
		for (int i=num_partic+1;i<n_particules+n_particules_composees;i++){
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null){
			dist_pos.assigne_soustrait(posit,teilchen[i].posit);
			if(Math.abs(dist_pos.x)<dist_lim_potmul2&&Math.abs(dist_pos.y)<2*dist_lim_potmul2){
			    dist_pos_prec.assigne_soustrait(posit_prec,teilchen[i].posit_prec);
			    sortie_du_potentiel=dist_pos_prec.longueur_carre()<dist_lim_pot2&&dist_pos.longueur_carre()>dist_lim_pot2;
			    if(dist_pos.longueur_carre()<dist_lim_pot2||sortie_du_potentiel){
				i_choc=i;
				teilchen[i].i_choc=num_partic;
				if(!sortie_du_potentiel){
				    if(dist_pos_prec.longueur_carre()<dist_lim_pot2){
					force.additionne_facteur(dist_pos,-raideur);
					teilchen[i].force.additionne_facteur(dist_pos,raideur);
				    }else{
					toto.assigne_soustrait(dist_pos,dist_pos_prec);
					cece=toto.longueur();
					if(cece>0.01){
					    coco=(dist_lim_pot-dist_pos.longueur())/cece;
					    force.additionne_facteur(dist_pos,-raideur*coco);
					    teilchen[i].force.additionne_facteur(dist_pos,raideur*coco);
					}
				    }
				}else{
				    toto.assigne_soustrait(dist_pos,dist_pos_prec);
				    coco=toto.longueur();
				    if(coco>0.01){
					coco=(dist_lim_pot-dist_pos_prec.longueur())/coco;
					force.additionne_facteur(dist_pos,-raideur*coco);
					teilchen[i].force.additionne_facteur(dist_pos,raideur*coco);
				    }
				}
			    }
			}
		    }
		}		
	    }
	    public void move(){
		vit.additionne_facteur(force,d_temps/masse);
		if(num_particule_mere==-1&&i_choc==-1)
		    d_depuis_dernier_choc += (vit.longueur()*d_temps);
		//if(interaction_nulle&&num_partic<5)
		//System.out.println("n_event "+n_event+" num_partic "+num_partic+" long_vit "+vit.longueur()+" d_depuis_dernier_choc "+d_depuis_dernier_choc);
		posit.additionne_facteur(vit,d_temps);
		toto.assigne_soustrait(posit,posit_prec);
		toto1.assigne_additionne(force,force_prec);
		toto1.multiplie_cst((float)0.5);
		travaux_elementaires+=toto1.scalaire(toto);
	    } 
	}
	class boule extends partic{
	    float dist_limx=0,dist_limy=0;
	    boule(point posit1,float mass,int ray,point vit1, int icouleur1,int nu_b){
		super(posit1,mass,ray,vit1,icouleur1,nu_b);
	    }
	    void defait_grosses_particules(){
		toto.assigne_soustrait(vit,vit_prec);
		dif_energie_dans_choc=(float)0.5*masse*toto.longueur_carre();
		energ_avant_defait=(float)0.5*masse*vit.longueur_carre();		
		energ_apres_defait=energ_avant_defait-energie_degagee-energie_interne;
		//float mm_cin_i=masse*posit.produit_vectoriel(vit);

		if(Math.abs(dif_energie_dans_choc)>barriere_annih&&(float)Math.random()>0.8&&energ_apres_defait>0){
		    vit.multiplie_cst((float)Math.sqrt(energ_apres_defait/energ_avant_defait));
		    float costet=(float)Math.random();
		    float sintet=(float)Math.sqrt(1-costet*costet);
		    float v=(float)Math.sqrt(2*(energie_interne/2)/teilchen[num_particule_fille1].masse);
		    toto.assigne(v*costet,v*sintet);
		    toto.rotation(vit.x/vit.longueur(),vit.y/vit.longueur());
		    toto.additionne(vit);
		    teilchen[num_particule_fille1].vit.assigne(toto);
		    toto.assigne(-v*costet,-v*sintet);
		    toto.rotation(vit.x/vit.longueur(),vit.y/vit.longueur());
		    toto.additionne(vit);
		    teilchen[num_particule_fille2].vit.assigne(toto);
		    elimi_g=true;
		    elimination[num_partic-n_particules]=true;
		    if(num_partic==(n_particules+n_particules_composees-1))
			n_particules_composees--;
		    teilchen[num_particule_fille1].num_particule_mere=-1; 
		    teilchen[num_particule_fille2].num_particule_mere=-1;
		    teilchen[num_particule_fille1].num_particule_soeur=-1; 
		    teilchen[num_particule_fille2].num_particule_soeur=-1; 
		    float energ_apres=(float)0.5*(teilchen[num_particule_fille1].masse*teilchen[num_particule_fille1].vit.longueur_carre()+teilchen[num_particule_fille2].masse*teilchen[num_particule_fille2].vit.longueur_carre());
		    //System.out.println("ùùùùùùùùùùùùù energ_avant_defait "+energ_avant_defait+" energ_apres_defait "+energ_apres_defait+" energ_apres "+energ_apres);
		    //System.out.println(" elimination de num_partic "+num_partic+" num_particule_fille1 "+num_particule_fi		    num_particule_fille1=-1;
		    num_particule_fille2=-1;
		}
	    }
	    void cree_grosses_particules(){
		for (int i=num_partic+1;i<n_particules;i++)
		    if((teilchen[i].num_particule_mere==-1)&&(teilchen[i].i_choc==-1)&&(teilchen[i].interieur_du_bocal())&&(!teilchen[i].deja_utilisee)&&!deja_utilisee){
			point dist_q=new point(posit);dist_q.soustrait(teilchen[i].posit);
			float sm=masse+teilchen[i].masse;
			float quatre_r2=(float)16.*rayon*rayon;float quatre_r=4*rayon;
			if(dist_q.x<quatre_r&&dist_q.x>-quatre_r&&dist_q.y<quatre_r&&dist_q.y>-quatre_r){
			    if(dist_q.longueur_carre()<quatre_r2){
				for (int jndice=teilchen[i].num_partic+1;jndice<n_particules;jndice++){
				    if((teilchen[jndice].num_particule_mere==-1)&&(teilchen[jndice].i_choc==-1)&&(teilchen[jndice].interieur_du_bocal())&&(!teilchen[jndice].deja_utilisee)&&!deja_utilisee){
					dist_q=new point(posit);dist_q.soustrait(teilchen[jndice].posit);
					if(dist_q.x<quatre_r&&dist_q.x>-quatre_r&&dist_q.y<quatre_r&&dist_q.y>-quatre_r){
					    float smj=masse+teilchen[jndice].masse;
					    toto.assigne_soustrait(teilchen[jndice].posit,teilchen[i].posit);
					    if(toto.x<quatre_r&&toto.x>-quatre_r&&toto.y<quatre_r&&toto.y>-quatre_r){
						if((dist_q.longueur_carre()<quatre_r2)&&(toto.longueur_carre()<quatre_r2)){
						    //float e_avant=masse*(vit.longueur_carre()+teilchen[i].vit.longueur_carre()+teilchen[jndice].vit.longueur_carre())/2;
						    toto.assigne_facteur(teilchen[i].vit,teilchen[i].masse/sm);
						    point  v12_cm=new point(vit,masse/sm);
						    v12_cm.additionne(toto);
						    point vvv_cm=new point(v12_cm,sm);
						    vvv_cm.additionne_facteur(teilchen[jndice].vit,teilchen[jndice].masse);
						    vvv_cm.multiplie_cst((float)1./(sm+teilchen[jndice].masse));
						    v_ds_cm_tot[0].assigne_soustrait(vit,vvv_cm);
						    v_ds_cm_tot[1].assigne_soustrait(teilchen[i].vit,vvv_cm);
						    v_ds_cm_tot[2].assigne_soustrait(teilchen[jndice].vit,vvv_cm);
						    float m_cine_i=masse*(posit.produit_vectoriel(v_ds_cm_tot[0])+teilchen[i].posit.produit_vectoriel(v_ds_cm_tot[1])+teilchen[jndice].posit.produit_vectoriel(v_ds_cm_tot[2]));
				//toto.assigne(v_ds_cm_tot[0]);toto.additionne(v_ds_cm_tot[1]);toto.additionne(v_ds_cm_tot[2]);toto.print(" toto ");
						    float en_cin_avant_cm=(masse*v_ds_cm_tot[0].longueur_carre()+teilchen[i].masse*v_ds_cm_tot[1].longueur_carre()+teilchen[jndice].masse*v_ds_cm_tot[2].longueur_carre())/2;
						    index[0]=num_partic;index[1]=teilchen[i].num_partic;index[2]=jndice;
						    int kmax=0;int kmin=0;int kmilieu=0;
						    for (int k=1;k<3;k++){
							if(v_ds_cm_tot[k].longueur_carre()> v_ds_cm_tot[kmax].longueur_carre())
							    kmax=k; 
							if(v_ds_cm_tot[k].longueur_carre()< v_ds_cm_tot[kmin].longueur_carre())
							    kmin=k;
						    }
						    for (int k=0;k<3;k++)
							if((k!=kmax)&&(k!=kmin))
							    kmilieu=k;
						    float en_cin_finale_cm=en_cin_avant_cm+energie_degagee;
						    if(en_cin_finale_cm>0){
							float sm_max_min=teilchen[index[kmax]].masse+teilchen[index[kmin]].masse;
							float m_inv=1/sm_max_min+1/teilchen[index[kmilieu]].masse;
							float p_milieu_cm=(float)Math.sqrt(2*en_cin_finale_cm/m_inv);
							v_ds_cm_tot[kmilieu].multiplie_cst(p_milieu_cm/teilchen[index[kmilieu]].masse/v_ds_cm_tot[kmilieu].longueur());
							cree_grosse=true;
							k_grosse=-1;
							for(int kk=0; kk<n_particules_composees;kk++)
							    if(elimination[kk]){
								k_grosse=n_particules+kk;
								break;
							    }
							if(k_grosse==-1){
							    k_grosse=n_particules+n_particules_composees;
							    n_particules_composees++;
							}
							atf.assigne_facteur(v_ds_cm_tot[kmilieu],-teilchen[index[kmilieu]].masse/sm_max_min);atf.additionne(vvv_cm);
							toto.assigne_additionne(teilchen[index[kmax]].posit,teilchen[index[kmin]].posit);toto.multiplie_cst((float)0.5);
							//float m_cine_labpf=masse*(teilchen[index[kmilieu]].posit.produit_vectoriel(teilchen[index[kmilieu]].vit)+2*teilchen[index[kmilieu]].posit.produit_vectoriel(atf));
							//float m_cine_grosse=masse*2*toto.produit_vectoriel(atf);
							//float m_cine_labf=masse*(teilchen[index[kmilieu]].posit.produit_vectoriel(teilchen[index[kmilieu]].vit)+2*toto.produit_vectoriel(atf));
							couleur cocol=new couleur(61,61,61);//gris pale
							teilchen[k_grosse]=new boule(toto,sm_max_min,rayon_grosse_particule,atf,15,k_grosse);
							if(!teilchen[k_grosse].interieur_du_bocal()){
							    print_en_cas_de_malheur(teilchen[k_grosse]);
							    cree_grosse=false;
							    teilchen[k_grosse]=null;
							    if(k_grosse==n_particules+n_particules_composees-1)
								n_particules_composees--;
							    k_grosse=-1;
							    break;
							}   
							teilchen[index[kmilieu]].vit.assigne_additionne(v_ds_cm_tot[kmilieu],vvv_cm);							
							//float e_apres=(sm_max_min*atf.longueur_carre()+teilchen[index[kmilieu]].masse*teilchen[index[kmilieu]].vit.longueur_carre())/2+energie_degagee;
							//System.out.println("e_avant "+e_avant+" e_apres "+e_apres+" en_cin_avant_cm "+en_cin_avant_cm+" en_cin_finale_cm "+en_cin_finale_cm);
							toto1.assigne_soustrait(teilchen[index[kmax]].posit,teilchen[k_grosse].posit);
							teilchen[index[kmax]].distance_a_la_mere.assigne_facteur(toto1,(float)0.5*rayon_grosse_particule/toto1.longueur());
							teilchen[index[kmin]].distance_a_la_mere.assigne_facteur(teilchen[index[kmax]].distance_a_la_mere,(float)-1.);
							teilchen[index[kmax]].posit.assigne_additionne(toto,teilchen[index[kmax]].distance_a_la_mere);
							teilchen[index[kmin]].posit.assigne_additionne(toto,teilchen[index[kmin]].distance_a_la_mere);
							
							// dans ce e_21_3_lab_f, on ne compte pas l'energie de liaison, qui sera restituée quand la prticule se défera.
							elimination[k_grosse-n_particules]=false;
							teilchen[index[kmax]].vit.assigne(atf);teilchen[index[kmin]].vit.assigne(atf);
							teilchen[k_grosse].num_particule_fille1=index[kmax];
							teilchen[k_grosse].num_particule_fille2=index[kmin];
							teilchen[k_grosse].num_particule_mere=-1;
							teilchen[k_grosse].ne_pas_defaire=true;
							teilchen[index[kmin]].deja_utilisee=true;
							teilchen[index[kmax]].deja_utilisee=true;
							teilchen[index[kmilieu]].deja_utilisee=true;
							teilchen[index[kmax]].num_particule_soeur=index[kmin];
							teilchen[index[kmin]].num_particule_soeur=index[kmax];
							int n1=index[kmax];int n2=index[kmin];
							float ener1=teilchen[n1].masse*teilchen[n1].vit.longueur_carre()/2;
							float ener2=teilchen[n2].masse*teilchen[n2].vit.longueur_carre()/2;
							teilchen[index[kmin]].num_particule_mere=k_grosse;
							teilchen[index[kmax]].num_particule_mere=k_grosse;
							return;
						    }
						}
					    }
					}
				    } 
				}  
			    } 
			}
		    }
	    }
	    void print_en_cas_de_malheur(partic ptn){
		ptn.posit.print("$$$$malheur! num_partic "+ptn.num_partic+" ptn.i_choc "+ptn.i_choc+"posit");
		ptn.vit.print("num_gaz "+num_gaz+" ptn.reflexion "+ptn.reflexion+" vit");
		System.out.println(" ptn.num_particule_mere "+ptn.num_particule_mere);
	    }
	    void compare_distances(){
		for (int i=num_partic+1;i<n_particules+n_particules_composees;i++){
		    if(i<n_particules&&teilchen[i].num_particule_mere==-1||i>=n_particules&&teilchen[i]!=null)
			if(sect_carre.h-teilchen[i].sect_carre.h<=1&&sect_carre.h-teilchen[i].sect_carre.h>=-1||paroi_mouvante&&sect_carre.h-teilchen[i].sect_carre.h<=2&&sect_carre.h-teilchen[i].sect_carre.h>=-2)
			    if(sect_carre.v-teilchen[i].sect_carre.v<=1&&sect_carre.v-teilchen[i].sect_carre.v>=-1||circulaire&&(sect_carre.v-teilchen[i].sect_carre.v==sect_carre.nb_secteurs-1||sect_carre.v-teilchen[i].sect_carre.v==-(sect_carre.nb_secteurs-1)))
				if(teilchen[i].num_particule_mere==-1)
				    //if((!paroi_mouvante)&&(sect_carre.h-teilchen[i].sect_carre.h<=1)&&(sect_carre.h-teilchen[i].sect_carre.h>=-1)){
				    if(selec_paire(teilchen[i])){
					toto.assigne_soustrait(posit,teilchen[i].posit);
					if(toto.longueur_carre()<(rayon+teilchen[i].rayon)*(rayon+teilchen[i].rayon)){
					    posit.print("num_partic "+num_partic+" posit ");
					    teilchen[i].posit.print("i "+i+" teilchen[i].posit ");
					    toto.print(" toto ");
					    teilchen[-2]=null;
					}   
					classement(num_partic);
					for (int k=0;k<nb_part_classees;k++)
					    if(obj_safe[k].nm_part==-1){
						System.out.println("nnnnnnnn num_partic "+num_partic+ " k "+k+" nb_part_classees "+nb_part_classees);
						teilchen[20000]=null;
					    }
					classement(i);

					for (int k=0;k<teilchen[i].nb_part_classees;k++)
					    if(teilchen[i].obj_safe[k].nm_part==-1){
						System.out.println("nnnnnnnn i "+i+ " k "+k+" teilchen[i].nb_part_classees "+teilchen[i].nb_part_classees);
						teilchen[20001]=null;
					    }
				    }
	    }
	    }
	    void classement(int nmp){
		if(teilchen[nmp].nb_part_classees<4){
		    //if(teilchen[nmp].obj_safe[teilchen[nmp].nb_part_classees]==null)
		    //teilchen[nmp].obj_safe[teilchen[nmp].nb_part_classees]=new objets_sauves();
		    vas_incr=false;
		    if(teilchen[nmp].nb_part_classees==0){
			teilchen[nmp].obj_safe[0].assigne(teilchen[nmp].obj_salv);
			vas_incr=true;
			if(teilchen[nmp].obj_safe[0].nm_part==-1){
			    System.out.println("$$$$$$$$$$$$$ num_partic "+num_partic);
			    teilchen[-1]=null;
			}
		    }else{
			for (int j=0;j<teilchen[nmp].nb_part_classees;j++){
			    if(teilchen[nmp].obj_salv.dt<teilchen[nmp].obj_safe[j].dt){
				for (int k=teilchen[nmp].nb_part_classees;k>j;k--){
				    teilchen[nmp].obj_safe[k].assigne(teilchen[nmp].obj_safe[k-1]);
				    if(teilchen[nmp].obj_safe[k].nm_part==-1){
					System.out.println("$$ num_partic "+num_partic+" j "+j+" k "+k);
					teilchen[18001]=null;
				    }
				}
				teilchen[nmp].obj_safe[j].assigne(teilchen[nmp].obj_salv);
				if(teilchen[nmp].obj_safe[j].nm_part==-1){
				    System.out.println("$$ num_partic "+num_partic+" j "+j);
				    teilchen[18002]=null;
				}
				vas_incr=true;
				break;
			    }
			}
		    }
		    if(vas_incr)
			if(teilchen[nmp].nb_part_classees<3)
			    teilchen[nmp].nb_part_classees++;
		}
	    }
            boolean selec_paire(partic pt_i){
		slc_pr=false;
		somme_ray=rayon+pt_i.rayon;
		dist_pos.assigne_soustrait(posit,pt_i.posit);
		dif_vit_dt.assigne_soustrait(vit,pt_i.vit);
		dif_vit_dt.multiplie_cst(d_temps);
		dist_limx=somme_ray+(float)Math.abs(dif_vit_dt.x);
		dist_limy=somme_ray+(float)Math.abs(dif_vit_dt.y);
		if((float)Math.abs(dist_pos.x)<dist_limx&&(float)Math.abs(dist_pos.y)<dist_limy){
		    d_pos_2_essai=dist_pos.longueur_carre();
		    if(d_pos_2_essai<dist_limx*dist_limx+dist_limy*dist_limy&&d_pos_2_essai>somme_ray*somme_ray){
			sm=masse+pt_i.masse;
			v_cm_essai.assigne_facteur(vit,masse);
			v_cm_essai.additionne_facteur(pt_i.vit,pt_i.masse);
			v_cm_essai.multiplie_cst((float)1./sm);
			v1_cm_essai.assigne_soustrait(vit,v_cm_essai);
			atf.assigne_soustrait(pt_i.vit,v_cm_essai);
			if(v1_cm_essai.scalaire(atf)<0.){
			    distance_transv=(float)Math.pow(dist_pos.produit_vectoriel(dif_vit_dt),2)/dif_vit_dt.longueur_carre();				
			    if(distance_transv<somme_ray*somme_ray){
				toto.assigne_facteur(pt_i.posit,pt_i.masse/sm);
				r_cm_essai.assigne_facteur(posit,masse/sm);
				r_cm_essai.additionne(toto);
				r1_cm_essai.assigne_soustrait(posit,r_cm_essai);
				//r1_cm_essai.print("num_partic "+num_partic+" i "+i+"distance_transv "+distance_transv+" r1_cm_essai ");
				if(r1_cm_essai.longueur_carre()<25*rayon*pt_i.rayon){
				    //if(r1_cm_essai.scalaire(v1_cm_essai)<0.){
					toto.assigne_facteur(r1_cm_essai,(float)1.-rayon/r1_cm_essai.longueur());
					toto.additionne_facteur(v1_cm_essai,d_temps);
					cece=somme_ray*pt_i.masse/sm;
					if(toto.longueur_carre()<(cece+rayon)*(cece+pt_i.rayon)){
					    
					    a_trinome=v1_cm_essai.longueur_carre();
					    bp_trinome=r1_cm_essai.scalaire(v1_cm_essai);
					    caca=pt_i.masse/sm*somme_ray;
					    c_trinome=r1_cm_essai.longueur_carre()-caca*caca;
					    delta_trinome=bp_trinome*bp_trinome-a_trinome*c_trinome;
					    dt_essai=1000;
					    if(delta_trinome>0){
						coco=(float)Math.sqrt(delta_trinome);
						cucu=(float)((-bp_trinome+coco)/a_trinome);
						cece=(float)((-bp_trinome-coco)/a_trinome);
						if(Math.abs(cucu)<Math.abs(cece))
						    dt_essai=cucu;
						else
						    dt_essai=cece;
						//System.out.println(" cece "+cece+" cucu "+cucu);
					    }
					    slc_pr=dt_essai<d_temps&&dt_essai>0;
					    if(slc_pr){
						attention_aux_bords=sect_carre.h==sect_carre.nb_secteurs-1||sect_carre.v==sect_carre.nb_secteurs-1||sect_carre.h==0||sect_carre.v==0;
						totologic=true;
						//if(totologic){						    
						    obj_salv.assigne(r_cm_essai,v_cm_essai,r1_cm_essai,v1_cm_essai,dt_essai,pt_i.num_partic);
						    r1_cm_essai.assigne_soustrait(pt_i.posit,r_cm_essai);
						    v1_cm_essai.assigne_facteur(v1_cm_essai,-pt_i.masse/masse);	
						    pt_i.obj_salv.assigne(r_cm_essai,v_cm_essai,r1_cm_essai,v1_cm_essai,dt_essai,num_partic);
						}
						//System.out.println("num_partic "+num_partic+" i "+i+" manque_diffusion "+manque_diffusion);
					    //}
					}
					// }
				}
			    }
			}
		    }
		}
		return slc_pr;
	    }
            boolean  selec_paire_bis(partic pt_i){
		return true;
	    }
	    void diffusion(){
		//System.out.println("num_partic  "+num_partic+" nnn "+nnn+" boo_pas_mere1 "+boo_pas_mere1);
		if(i_choc!=-1||num_particule_mere!=-1)
		    return;
		boolean fait=false;
		for (int k=0;k<nb_part_classees;k++){
		    int j=obj_safe[k].nm_part;
		    if(j==-1)
			System.out.println("***************** num_partic "+num_partic+ " j "+j+" nb_part_classees "+nb_part_classees);
		    if(teilchen[j]!=null)
			if(teilchen[j].i_choc==-1&&teilchen[j].num_particule_mere==-1){
			    //System.out.println(" nb_part_classees "+nb_part_classees+" teilchen[j].nb_part_classees "+teilchen[j].nb_part_classees);
			    for (int kk=0;kk<teilchen[j].nb_part_classees;kk++){
				int num_choc=teilchen[j].obj_safe[kk].nm_part;
				if(num_choc==num_partic&&obj_safe[k].dt<=obj_safe[kk].dt){
				    totologic=true;
				    if(totologic)//s'il n'y a pas diffusion, il y aura reflexion!
					fait=diffusion_boules(teilchen[j],obj_safe[k],teilchen[j].obj_safe[kk]);
				    break;
				    //chcheck(j);
				}else
				    break;
			    }
			    if(fait)
				break;
			}
		}
	    }
	    boolean diffusion_boules(partic pt_i,objets_sauves obj,objets_sauves obj_oppose){
		toto.assigne(obj.r1_cm);
		toto.additionne_facteur(obj.v1_cm,obj.dt);
		new_position.assigne(toto);
		new_vitesse.assigne_facteur(obj.v1_cm,(float)-1.);
		toto.multiplie_cst((float)1./toto.longueur());
		new_vitesse.symetrique(toto);
		i_choc=obj.nm_part;pt_i.i_choc=obj_oppose.nm_part;
		boolean renonce_spins=false;
		if(avec_spin){
		    mom_cin_orbital_i=masse*(1+masse/pt_i.masse)*new_position.produit_vectoriel(new_vitesse); 
		    somme_des_spins_choc +=(spin+pt_i.spin);
		    somme_des_mom_orb_choc +=mom_cin_orbital_i;
		    n_chocs_event++;
		    e_orb_tot_i=(double)((1+masse/pt_i.masse)*masse*obj.v1_cm.longueur_carre()/2);
		    e_spin=spin*spin/(2*moment_d_inertie);
		    pt_i.e_spin=pt_i.spin*pt_i.spin/(2*pt_i.moment_d_inertie);
		    e_spin_tot_i=(double)(e_spin+pt_i.e_spin);
		    somme_spin_i=spin+pt_i.spin;
		    dmom=facteur_spin*somme_spin_i+facteur_mm_cn_orb*mom_cin_orbital_i;
		    //float dmom=facteur_spin*somme_spin_i;
		    e_orb_tot_f=-1;
		    ptns=spin;ptis=pt_i.spin;
		    dmom *=2;int n_b=0;
		    while(e_orb_tot_f<0){
			dmom /=2;n_b++;
			spin =ptns+dmom;
			pt_i.spin =ptis+dmom;
			e_spin=spin*spin/(2*moment_d_inertie);
			pt_i.e_spin=pt_i.spin*pt_i.spin/(2*pt_i.moment_d_inertie);
			e_spin_tot_f=(double)(e_spin+pt_i.e_spin);
			e_orb_tot_f=e_orb_tot_i+e_spin_tot_i-e_spin_tot_f;
		    }
		    mom_cin_orbital_f=mom_cin_orbital_i-2*dmom;
		    nvv=new_vitesse.longueur();
		    masse_inv=1/(2*masse)+1/(2*pt_i.masse);
		    vnew=(float)Math.sqrt(e_orb_tot_f/masse_inv)/masse;
		    new_vitesse.multiplie_cst(vnew/nvv);
		    npp=new_position.longueur();
		    nvv_f=new_vitesse.longueur();
		    if((nvv_f>0.00001)&&(npp>0.1)){
			s_angle_i=mom_cin_orbital_i/(masse*nvv*((1+masse/pt_i.masse)*npp));
			s_angle_f=mom_cin_orbital_f/(masse*nvv_f*((1+masse/pt_i.masse)*npp));
			if(((float)Math.abs(s_angle_f)<0.99)&&((float)Math.abs(s_angle_i)<0.999)){
			    float angle_f_i=(float)Math.asin(s_angle_f)-(float)Math.asin(s_angle_i);
			    //System.out.println(" s_angle_i "+s_angle_i+" s_angle_f "+s_angle_f+" angle_f_i "+angle_f_i);
			    new_vitesse.rotation(angle_f_i);
			    //float mm_cn=masse*(1+masse/pt_i.masse)*new_position.produit_vectoriel(new_vitesse);
			    //System.out.println(" mom_cin_orbital_i "+mom_cin_orbital_i+" mom_cin_orbital_f"+mom_cin_orbital_f+" mm_cn "+mm_cn);
			}else{
			    renonce_spins=true;  
			    //System.out.println(" s_angle_i "+s_angle_i+" s_angle_f "+s_angle_f);
			}
		    }else{
			System.out.println(" dmom "+dmom+" somme_spin_i "+somme_spin_i+" renonce_spins "+renonce_spins+" somme_spin_f "+somme_spin_f);
			renonce_spins=true;  
		    }
		}
		position_juste_apres_choc.assigne(new_position);
		pt_i.position_juste_apres_choc.assigne_facteur(position_juste_apres_choc,-masse/pt_i.masse);
		new_position.additionne_facteur(new_vitesse,d_temps-obj.dt);   
		pt_i.new_position.assigne_facteur(new_position,-masse/pt_i.masse);
		pt_i.new_vitesse.assigne_facteur(new_vitesse,-masse/pt_i.masse);
		//System.out.println("obj.manque_diffusion "+obj.manque_diffusion+" num_partic "+num_partic+" pt_i.num_partic "+pt_i.num_partic);
		//float e_o_f=(masse*new_vitesse.longueur_carre()+pt_i.masse*pt_i.new_vitesse.longueur_carre())/2;
		//float e_s_f=e_spin+pt_i.e_spin;
		new_vitesse.additionne(obj.v_cm);
		new_position.additionne(obj.r_cm);
		pt_i.new_vitesse.additionne(obj.v_cm);
		pt_i.new_position.additionne(obj.r_cm);
		position_juste_apres_choc.additionne(obj.r_cm);
		pt_i.position_juste_apres_choc.additionne(obj.r_cm);
		if(!boqal.est_dedans(position_juste_apres_choc,rayon)||!boqal.est_dedans(pt_i.position_juste_apres_choc,rayon)){
		    i_choc=-1;pt_i.i_choc=-1;
		    return false;
		}
		if(isyst==0&&(new_position.x<boqal.x_gauche+rayon||new_position.x>boqal.x_droite-rayon||new_position.y>boqal.hauteur/2-rayon||new_position.y<-boqal.hauteur/2+rayon)){
		    if(pt_i.new_position.x<boqal.x_gauche+rayon||pt_i.new_position.x>boqal.x_droite-rayon||pt_i.new_position.y>boqal.hauteur/2-rayon||pt_i.new_position.y<-boqal.hauteur/2+rayon){
			new_position.print(" n_event "+n_event+" num_gaz "+num_gaz+" num_partic "+num_partic+" new_position ");
			new_vitesse.print(" new_vitesse ");
			pt_i.new_position.print(" n_event "+n_event+" num_gaz "+num_gaz+" pt_i.num_partic "+pt_i.num_partic+" pt_i.new_position ");
			pt_i.new_vitesse.print(" pt_i.new_vitesse ");
		    }
		}

		if((num_partic>=n_particules)||(i_choc>=n_particules)){
		    a_diffuse=true;

		    return true;
		}
		return true;
		//System.out.println(" avant diffusion num_partic "+num_partic+" pt_i.num_partic "+pt_i.num_partic+" i_choc "+i_choc+" pt_i.i_choc "+pt_i.i_choc);
	    }
	    void move(){
		if(i_choc==-1&&num_particule_mere==-1){
		    dposit.assigne_facteur(vit,d_temps);
		    posit.additionne(dposit);
		    
		    if(num_partic>=n_particules){
			teilchen[num_particule_fille1].posit.additionne(dposit);
			teilchen[num_particule_fille2].posit.additionne(dposit);
		    }
		}else
		    dposit.assigne(point_zer0);
		//if(avant)
		if(num_particule_mere==-1)
		    d_depuis_dernier_choc += (long_vit*d_temps);
		if(i_choc!=-1){
		    //System.out.println("$$$$ move num_partic "+num_partic+" i_choc "+i_choc);
		    if(num_partic>=n_particules){
			toto.assigne_additionne(new_position,teilchen[num_particule_fille1].distance_a_la_mere);
			teilchen[num_particule_fille1].posit.assigne(toto);
			toto.assigne_additionne(new_position,teilchen[num_particule_fille2].distance_a_la_mere);
			teilchen[num_particule_fille2].posit.assigne(toto);
			teilchen[num_particule_fille1].vit.assigne(new_vitesse);
			teilchen[num_particule_fille2].vit.assigne(new_vitesse);
			//System.out.println("$$$$num_partic "+num_partic+" num_particule_fille1 "+num_particule_fille1+" num_particule_fille2 "+num_particule_fille2);
		    }
		    posit.assigne(new_position);
		    vit.assigne(new_vitesse);
		    long_vit=vit.longueur();
		}
	    } 
	}
	class objets_sauves implements Cloneable{
	    point r_cm,v_cm,r1_cm,v1_cm;
	    float dt=0;
	    int nm_part=-1;
	    objets_sauves(){
		r_cm=new point(point_zer0);
		r1_cm=new point(point_zer0);
		v_cm=new point(point_zer0);
		v1_cm=new point(point_zer0);
	    }
	    public Object clone(){
		try{
		    e_objets_sauves=(objets_sauves)super.clone();
		    e_objets_sauves.r_cm=(point)r_cm.clone();
		    e_objets_sauves.r1_cm=(point)r1_cm.clone();
		    e_objets_sauves.v_cm=(point)v_cm.clone();
		    e_objets_sauves.v1_cm=(point)v1_cm.clone();
		    return e_objets_sauves;
		}
		catch (CloneNotSupportedException e){
		    return null;
		}

	    }
	    void assigne(point r_cm_s,point v_cm_s,point r1_cm_s,point v1_cm_s,float dt_s,int nmpp){
		r_cm.assigne(r_cm_s);
		v_cm.assigne(v_cm_s);
		r1_cm.assigne(r1_cm_s);
		v1_cm.assigne(v1_cm_s);
		dt=dt_s;
		nm_part=nmpp;
		if(nm_part==-1)
		    teilchen[10000]=null;
	    }
	    void assigne(objets_sauves b){
		r_cm.assigne(b.r_cm);
		v_cm.assigne(b.v_cm);
		r1_cm.assigne(b.r1_cm);
		v1_cm.assigne(b.v1_cm);
		dt=b.dt;
		nm_part=b.nm_part;
		if(nm_part==-1)
		    teilchen[10001]=null;
	    }
	    void print(String st){
		System.out.println(st+" longueurs "+r_cm.longueur()+" "+v_cm.longueur()+" "+r1_cm.longueur()+" "+v1_cm.longueur());
		System.out.println(st+" dt "+dt+" nm_part "+nm_part);
	    }

	}
    }
    class secteur implements Cloneable{
	int h,v,nb_secteurs;
	secteur(int h1,int v1,int nb_secteurs1){
	    h=h1;v=v1;nb_secteurs=nb_secteurs1;
	}
	public Object clone(){
	    try{
		//point e=(point)super.clone();
		//return e;
		return super.clone();
	    }
	    catch (CloneNotSupportedException e){
		return null;
	    }
	}
	public void print(String st){
	    System.out.println(st+ " h "+h+" v "+v+" nb_secteurs "+nb_secteurs);
	}
	public void assigne(int h1,int v1){
	    h=h1;v=v1;
	}
	public void assigne(secteur s){
	    if(nb_secteurs==s.nb_secteurs){
		h=s.h;v=s.v;
	    }else{
		print("  " );
		s.print(" s " );
		gas[10]=null;		
	    }	
	}
	public void assigne(point pst, float[] ra_2){
	    h=nb_secteurs;
	    for (int k=0;k<nb_secteurs;k++){
		if(pst.longueur_carre()<=ra_2[k]+0.01){
		    h=k;
		    break;
		}
	    }	    
	    v=(int)((pst.direction()+180.)/360.*8);
	    if(v>=nb_secteurs||v<0)
		if(pst.direction()==(float)-180.)
		    v=0;
		else if(pst.direction()==(float)180.)
		    v=nb_secteurs-1;
		else{
		    System.out.println("v "+v +" pst.direction()  "+pst.direction());
		    gas[1000]=null;
		}
	}
	public void assigne(point pst,float x_gauche,float x_droite,int rayon_prtc,float hauteur){
	    float rap=(pst.x-(x_gauche+rayon_prtc))/(x_droite-x_gauche-2*rayon_prtc);
	    if(rap>=(float)1.&&rap<=(float)1.0000001)
		rap=(float)0.9999;
	    if(rap<=(float)0.&&rap>=-(float)0.0000001)
		rap=(float)0.;
	    if(rap>=0)
		h=(int)(rap*nb_secteurs);
	    else{
		h=-1;
		//pst.print("n_event "+n_event+" h "+h+" rap "+rap+" rayon_prtc "+rayon_prtc+" x_droite "+x_droite+" x_gauche "+x_gauche+" nb_secteurs "+nb_secteurs+" pst ");
	    }
	    //if(h>7||h<0)
	    //xsSystem.out.println("h "+h +" pst.x  "+pst.x+" x_gauche "+x_gauche+" x_droite "+x_droite+" rap "+rap);
	    rap=(pst.y+hauteur/2-rayon_prtc)/(hauteur-2*rayon_prtc);
	    if(rap>=(float)1.&&rap<=(float)1.0000001)
		rap=(float)0.9999;
	    if(rap<=(float)0.&&rap>=-(float)0.0000001)
		rap=(float)0.;
	    if(rap>=0)
		v=(int)(rap*nb_secteurs);
	    else{
		v=-1;
		//System.out.println("v "+v +" pst.y  "+pst.y+" hauteur "+hauteur+" rap "+rap+" nb_secteurs "+nb_secteurs);
	    }
	}
	public void assigne_print(point pst, float x_gauche,float x_droite,int rayon_prtc,float hauteur){
	    float rap=(pst.x-(x_gauche+rayon_prtc))/(x_droite-x_gauche-2*rayon_prtc);
	    if(rap>=(float)1.&&rap<=(float)1.0000001)
		rap=(float)0.9999;
	    if(rap<=(float)0.&&rap>=-(float)0.0000001)
		rap=(float)0.;
	    if(rap>=0)
		h=(int)(rap*nb_secteurs);
	    else
		h=-1;
	    //if(h>7||h<0)
	    //xsSystem.out.println("h "+h +" pst.x  "+pst.x+" x_gauche "+x_gauche+" x_droite "+x_droite+" rap "+rap;)
	    rap=(pst.y+hauteur/2-rayon_prtc)/(hauteur-2*rayon_prtc);
	    System.out.println(" pst.y  "+pst.y+" hauteur "+hauteur+" rap "+rap+" nb_secteurs "+nb_secteurs);
	    if(rap>=(float)1.&&rap<=(float)1.0000001)
		rap=(float)0.9999;
	    if(rap<=(float)0.&&rap>=-(float)0.0000001)
		rap=(float)0.;
	    if(rap>=0)
		v=(int)(rap*nb_secteurs);
	    else
		v=-1;
	    //if(v>7||v<0)
	    System.out.println("v "+v +" rap "+rap+" nb_secteurs "+nb_secteurs);
	}
    }
    
    public void	traite_click(){
	System.out.println(" ppmouseh "+ppmouseh+" ppmousev "+ppmousev);
	if(cliquee){
	    vient_de_cliquer=true;
	    if(!machina_vapore)
		cliquee=false;	    
	    if((ppmouseh>=10)&&(ppmouseh<=right_pression)&&(ppmousev<=bottom_pression)&&(ppmousev>=bottom_pression-8))
		 n_parametre_a_voir=0;
	    if((ppmouseh>=10)&&(ppmouseh<=right_temperature)&&(ppmousev<=bottom_temperature)&&(ppmousev>=bottom_temperature-8))
		 n_parametre_a_voir=1;
	    if((ppmouseh>=10)&&(ppmouseh<=right_nTsp)&&(ppmousev<=bottom_nTsp)&&(ppmousev>=bottom_nTsp-8))
		 n_parametre_a_voir=2;
	    if((ppmouseh>=10)&&(ppmouseh<=right_spins)&&(ppmousev<=bottom_spins)&&(ppmousev>=bottom_spins-8))
		 n_parametre_a_voir=4;
	    if((ppmouseh>=10)&&(ppmouseh<=right_libre_p)&&(ppmousev<=bottom_libre_p)&&(ppmousev>=bottom_libre_p-8))
		 n_parametre_a_voir=5;
	    if(n_parametre_a_voir!=-1){
		voir_parametre=0;
		n_parametre_a_voir_prec=n_parametre_a_voir;
		if(n_parametre_a_voir!=3){
		    if(paramt==null)
			open_close_reopen(st_comm[0],true,false,false);
		    paramt.ecrit_aide(n_parametre_a_voir);
		}
	    }
	}
    }
    class MouseStatic extends MouseAdapter{
	systeme subject;
	public MouseStatic (systeme a){
	    subject=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_cyl[icylindre].nb_el_ens "+ens_de_cyl[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_cyl[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;cliquee=false;relachee=false;
	    System.out.println("pressee "+pressee+" ppmouseh "+ppmouseh+" ppmousev "+ppmousev );
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;pressee=false;
	    traite_click();
	    System.out.println("relachee "+relachee);
	}
    }
    abstract class commentaire_aide extends graphes_spectres{
	int ordonnee_comm[]=new int[10];Graphics gTTampon_comm;
	String nom_fichier;
	MediaTracker tracker;
	public commentaire_aide(String s,int numg1){
	    super(s,numg1);
	}
	abstract void ecrit_aide(int index);
    }
    class parametre extends commentaire_aide{
	Graphics grp_param;int ind=-1;
	int top=500;int left=400;int bottom = 750;int right = 920;
	int num_lignes_speciales=0;boolean image_dessinee=false;
	public parametre(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_param_comm_a_virer=numg;
		    };
		});		
	    pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grp_param= getGraphics();
	    setVisible(true);
	    ordonnee_comm[0]=0+50;
	    ordonnee_comm[1]=-200+50;
	    ordonnee_comm[2]=-250+50;
	    ordonnee_comm[4]=-500+50;
	    ordonnee_comm[5]=-550+50;
	    grp_param.drawImage(appelant.image_comm,0,50,null);      
	}
	void abandon(){
	    n_parametre_a_voir=-1;
	    paramt.dispose();
	    paramt=null;
	    quel_comm[numg]=null;
	}
	public void ecrit_aide(int ind){
	    setVisible(true);
	    appelant.eraserect(grp_param,0,0,500,500,Color.white);
	    grp_param.drawImage(appelant.image_comm,20,ordonnee_comm[ind],null);
	}
    }
    class parametre_entropie extends commentaire_aide{
	int top=50,left=350,bottom = 850,right = 900;
	int num_lignes_speciales=0;boolean image_dessinee=false;
	Graphics grp_param_entropie;
	public parametre_entropie(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_param_comm_a_virer=numg;
		    };
		});		
	    pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grp_param_entropie= getGraphics();
	    setVisible(true);
	    ordonnee_comm[3]=-600;
	    grp_param_entropie.drawImage(appelant.image_entropie,0,0,null);
	    
	}
	void abandon(){
	    n_parametre_a_voir=-1;
	    paramt_entropie.dispose();
	    paramt_entropie=null;
	    quel_comm[numg]=null;
	}
	public void ecrit_aide(int ind){
	    setVisible(true);
	    appelant.eraserect(grp_param_entropie,0,0,1000,1000,Color.white);
	    grp_param_entropie.drawImage(appelant.image_entropie,20,0,null);
	}
    }

    class explications extends commentaire_aide{
	Graphics grp_explications;
	int top=450,left=0,bottom = 950,right = 470;
	public explications(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_param_comm_a_virer=numg;
		    };
		});		
	    pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grp_explications= getGraphics();
	    setVisible(true);
	}
	void abandon(){
	    explications_bitte.dispose();
	    explications_bitte=null;
	    quel_comm[numg]=null;
	}
	public void ecrit_aide(int bidon){
		grp_explications.drawImage(image_explications_noires,10,30,null);
		int titi_int=150+30+35;
		if(nb_de_gaz==2)
		    titi_int=150+30+40;
		grp_explications.drawImage(image_explications_rouges,10,titi_int,null);
	}
    }

    class aider extends commentaire_aide{
	Graphics grp_aide;Image image_aide;
	int top=500,left=470,bottom = 800,right = 940;
	public aider(String s,int numg1){
	    super(s,numg1);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			num_param_comm_a_virer=numg;
		    };
		});		

	    pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    grp_aide= getGraphics();
	    setVisible(true);
	    grp_aide.setColor(Color.black);
	    index_courant_commentaire=0;
	}
	void abandon(){
	    a_l_aide_svp.dispose();
	    a_l_aide_svp=null;
	    quel_comm[numg]=null;
	}
	public void ecrit_aide(int index){
	    if(index!=-2)
		nom_fichier="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_aide_speciale_"+index+".jpg";
	    else
		nom_fichier="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/meca_float_speciale_demarre_"+i_demarre+".jpg";
	    image_aide=createImage(450,350);
	    gTTampon_comm=image_aide.getGraphics();
	    image_aide=Toolkit.getDefaultToolkit().getImage(nom_fichier);
	    tracker=new MediaTracker(this);
	    tracker.addImage(image_aide,0); 
	    try {tracker.waitForID(0); }
	    catch (InterruptedException e) {
		System.out.println(" image pas arrivee?");
	    }
	    grp_aide.drawImage(image_aide,10,30,null);
	    
	}
    }
    class point implements Cloneable{
	float x,y;    
	point(float xi, float yi){
	    x=xi;y=yi;
	}
	point(point a){
	    x=a.x;y=a.y;
	}
	point(point a,float b){
	    x=a.x*b;y=a.y*b;
	}
	public Object clone(){
	    try{
		//point e=(point)super.clone();
		//return e;
		return super.clone();
	    }
	    catch (CloneNotSupportedException e){
		return null;
	    }
	    
	}
	public float direction(){
	    float angle=0;
	    if((float)Math.abs(x)>(float)Math.abs(y)){
		angle=(float)180./pi*(float)Math.asin(y/longueur());
		if(x<0.)
		    if(y>0)
			angle=(float)180.-angle;
		    else
			angle=-(float)180.-angle;
	    }else{
		angle=(float)180./pi*(float)Math.acos(x/longueur());
		if(y<0.)angle=-angle;
	    }
	    return angle;
	}
	public void assigne(float xi, float yi){
	    x=xi;y=yi;
	}
	public void assigne(point a){
	    x=a.x;y=a.y;
	}
	public void assigne_oppose(point a){
	    x=-a.x;y=-a.y;
	}
	public void assigne_additionne(point a,point b){
	    x=a.x+b.x;y=a.y+b.y;
	}
	public void assigne_soustrait(point a,point b){
	    x=a.x-b.x;y=a.y-b.y;
	}
	public void assigne_facteur(point a,float b){
	    x=a.x*b;y=a.y*b;
	}
	public void assigne_diviseur(point a,float b){
	    x=a.x/b;y=a.y/b;
	}
	public float distance_carre(point pt){
	    float d;
	    d=(float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2);
	    return d;
	}
	public void multiplie_cst(float a){
	    x*=a;
	    y*=a;
	}
	public void additionne(float xx,float yy){
	    x+=xx;
	    y+=yy;
	}
	public void additionne(point a){
	    x+=a.x;
	    y+=a.y;
	}
	public void soustrait(point a){
	    x-=a.x;
	    y-=a.y;
	}
	public void soustrait(float xx,float yy){
	    x-=xx;
	    y-=yy;
	}
	public void additionne_facteur(point a,float b){
	    x+=b*a.x;
	    y+=b*a.y;
	}
	public void soustrait_facteur(point a,float b){
	    x-=b*a.x;
	    y-=b*a.y;
	}
	public float distance(point pt){
	    float d;
	    d=(float)Math.sqrt((float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2));
	    return d;
	}
	public float longueur(){
	    return ((float)Math.sqrt(x*x+y*y));
	}
	public float longueur_carre(){
	    return ((float)Math.pow(x,2)+(float)Math.pow(y,2));
	}
	public float scalaire(point a){
	    return x*a.x+y*a.y;
	}
	public float produit_vectoriel(point a){
	    return x*a.y-y*a.x;
	}
	public void rotation(float angle){
	    float cos=(float)Math.cos(angle);float sin=(float)Math.sin(angle);
	    float x_p=x;float y_p=y;
	    x=cos*x_p-sin*y_p;
	    y=sin*x_p+cos*y_p;
	}
	public void rotation(float c_ang,float s_ang){
	    float x_p=x;float y_p=y;
	    x=c_ang*x_p-s_ang*y_p;
	    y=s_ang*x_p+c_ang*y_p;
	}
	public void symetrique(point d){
	    coco=scalaire(d);
	    cucu=produit_vectoriel(d);
	    x=d.x*coco-d.y*cucu;
	    y=+d.x*cucu+d.y*coco;
	}
	public void print(String st){
	    float xx=(float)x;float yy=(float)y;float l=(float)longueur();
	    System.out.println(st+ " x "+xx+" y "+yy+" l. "+l);
	}
    }
    class couleur {
	Color col;
	int r,v,b;
	
	public couleur(int r1,int v1,int b1){
	    r=r1;v=v1;b=b1;
	    col=new Color(r,v,b);
	    //	    if(col==rouge) marche pas!!!!!
	}
	public couleur(couleur a){
	    r=a.r;v=a.v;b=a.b;
	    col=new Color(r,v,b);
	    //	    if(col==rouge) marche pas!!!!!
	}
	public void assigne(couleur c){
	    col=c.col;
	    r=c.r;v=c.v;b=c.b;
	}
	public void print(String st){
	    System.out.println(st+ " r "+r+" v "+v+" b "+b);
	}
	public boolean egale(couleur a){
	    return ((r==a.r)&&(v==a.v)&&(b==a.b));
	}
    }
}

//http://delcourt.benoit.over-blog.fr/article-12600906-6.html#comment82216343
