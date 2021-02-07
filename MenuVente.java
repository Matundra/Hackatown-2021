/*Fichier MenuVente.java 

   auteur : Mathieu Trudeau et Gabriel Sincan

   Ce programme permet de créer un JFrame ressortant toutes les interactions (entrée et sortie) du programme, à travers une interface graphique et ayant 
   des raccourcis et des saisies manuelles

   Derniere mise a jour : 7 février 2021
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class MenuVente extends JFrame implements ActionListener
{
	TestInterface laBase = new Bdd();
	private JMenuBar mb;
	
	JPanel panelVente = new JPanel();
	JPanel panelProduit = new JPanel();
	
	private JButton unBouton;
	
	public MenuVente(int l, int h, String title)
	{
		setTitle(title);
		setSize(l,h);
		
		//Création de 1 JMenuBar et de 2 JMenuItem
		mb = new JMenuBar();
		JMenuItem mi;
		JMenuItem miQuitter;
		
		//Menus Recherche,Lecture,Ecriture
		JMenu recherche = new JMenu("Recherche");
		
		JMenu ecriture = new JMenu ("Ecriture");
		
		JMenu lecture = new JMenu ("Lecture");
		lecture.addActionListener(this);			
		
		//sous menu telephone
		JMenu telephone = new JMenu("Une vente de jardin");
		
			mi = new JMenuItem("Afficher une vente de jardin");
			mi.addActionListener(this);
			telephone.add(mi);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 2));
		
		//sous menu toutVente
		JMenu toutVente = new JMenu("Toutes les ventes de jardin");
		
			mi = new JMenuItem("Ayant comme produit...");
			mi.addActionListener(this);
			toutVente.add(mi);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 2));
			
			mi = new JMenuItem("Correspondant au quartier...");
			mi.addActionListener(this);
			toutVente.add(mi);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 2));
			
			mi = new JMenuItem("Ayant comme date...");
			mi.addActionListener(this);
			toutVente.add(mi);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 2));

		//Menu Item sous Ecriture
			mi = new JMenuItem("Sauvegarde bdd dans fichier (saveBdd)");
			mi.addActionListener(this);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));
			ecriture.add(mi);
		
			
		//Menu Items sous Lecture
			mi = new JMenuItem("Charger une bdd (loadBdd)");
			mi.addActionListener(this);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 2));
			lecture.add(mi);
			
			mi = new JMenuItem("Ajouter infos à la bdd (addBdd)");
			mi.addActionListener(this);
			mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 2));
			lecture.add(mi);			
		
		
		//Menu Item À propos de Ton-marché
		mi = new JMenuItem("À propos de Ton-marché");
		mi.addActionListener(this);
		mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 2));
		
		//Menu Item Quitter
		miQuitter = new JMenuItem("Quitter");
		miQuitter.addActionListener(this);
		miQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,2));
		
		//organisation du menu Recherche
		recherche.add(telephone);
		recherche.add(toutVente);
		
		//composition de la barre de menu
		mb.add(recherche);
		mb.add(ecriture);
		mb.add(lecture);
		mb.add(mi);
		mb.add(miQuitter);
		setJMenuBar(mb);		
		
		//JPanel panelVente
		panelVente.setBorder
			(
				BorderFactory.createTitledBorder
				(
						BorderFactory.createEtchedBorder(),
						"Ajouter une vente de jardin a la Base de donnee (Bdd)"
				)
			);
			
		//Composantes du panelVente
			//Texte date
			JLabel labelDate = new JLabel("Date (AA-MM-DD): ");
			JTextField date = new JTextField(20);			
			panelVente.add(labelDate);
			panelVente.add(date);
			
			//Texte telephone
			JLabel labelTel = new JLabel("Telephone (AAA-BBB-CCCC): ");
			JTextField tel = new JTextField(30);
			panelVente.add(labelTel);
			panelVente.add(tel);
			
			//Texte lieu
			JLabel labelLieu = new JLabel("Quartier (1ere lettre majuscule.Ex:Rosemont/): ");
			JTextField lieu = new JTextField(5);
			panelVente.add(labelLieu);
			panelVente.add(lieu);
			
			//Bouton addVente
			panelVente.add(unBouton=new JButton("Ajouter votre vente de jardin !"));
			unBouton.addActionListener(this);
			Set<Vente>tabObjet=new HashSet<Vente>();
			
			unBouton.addActionListener
			(
				new ActionListener()
				{
					public void actionPerformed (ActionEvent e)
					{						
						if(!(date.getText().equals("")) && !(tel.getText().equals("")) && !(lieu.getText().equals("")))
						{						
							Vente uneVente = new Vente(date.getText(),tel.getText(),lieu.getText());					
							laBase.addVente(uneVente);
							tabObjet.add(uneVente);
							JOptionPane.showConfirmDialog
							(
								panelVente,
								uneVente,
								"Vente ajouté !",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.INFORMATION_MESSAGE
							);
						}
						else
							JOptionPane.showConfirmDialog
							(
								panelVente,
								"Veuillez remplir toutes les cases",
								"Erreur d'ajout !",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE
							);
					}				
				}
			);
			
		//JPanel panelProduit
		panelProduit.setBorder
		(
			BorderFactory.createTitledBorder
			(
				BorderFactory.createEtchedBorder(),
				"Ajouter un produit a une vente spécifique préexistante (Tapez sa date et son telephone pour la recherche...)"
			)
		);
		
		//Composantes du JPanel panelProduit			
			//Texte date
			JLabel labelDateProd = new JLabel("Date (AA-MM-DD): ");
			JTextField dateProd = new JTextField(20);
			panelProduit.add(labelDateProd);
			panelProduit.add(dateProd);
			
			//Texte telephone
			JLabel labelTelProd = new JLabel("Telephone (AAA-BBB-CCCC): ");
			JTextField telProd = new JTextField(30);
			panelProduit.add(labelTelProd);
			panelProduit.add(telProd);			
			
			//Texte Produit:
			JLabel labelProduit = new JLabel("Produit (1 à la fois/Générique/aucune majuscule/singulier Ex:pomme): ");
			JTextField produit = new JTextField(30);
			panelProduit.add(labelProduit);
			panelProduit.add(produit);
		
			//Boutton addProduit
			panelProduit.add(unBouton=new JButton("Ajouter le produit à votre vente préexistante"));			
			unBouton.addActionListener
			(
				new ActionListener()
				{
					public void actionPerformed (ActionEvent e)
					{							 
						int i=-1;
						Vente res;
						Iterator <Vente>k = tabObjet.iterator();			
						while(k.hasNext())
						{						
							res=k.next();
							if(!(produit.getText().equals("")) && dateProd.getText().equals(res.getDate()) && telProd.getText().equals(res.getTel()))
							{								
								res.addProduit(produit.getText());
								JOptionPane.showConfirmDialog
								(
									panelProduit,
									res,
									"Produit ajouté",
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.INFORMATION_MESSAGE
								);
								i=1;
							}	
						}
						if(i<0 || produit.getText().equals("") || dateProd.getText().equals("") || telProd.getText().equals(""))
						{
							JOptionPane.showConfirmDialog
							(
								panelProduit,
								"Cases vides et/ou vente non trouvée",
								"Produit NON ajouté !",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE
							);
						}
					}				
				}
			);
			//JLabel consigne
			JLabel consigne= new JLabel("ATTENTION: La vente doit avoir été déjà ajoutée par le bouton \"Ajouter votre vente de jardin !\"!");
			panelProduit.add(consigne);
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			
		setLayout(new GridLayout(2,0));		
		
		//Ajout des panels dans la fenetre
		add(panelVente,BorderLayout.CENTER);
		add(panelProduit,BorderLayout.CENTER);
		
		setVisible(true);
		mb.requestFocusInWindow();
	} //Fin du constructeur
	
	public void actionPerformed(ActionEvent e)
	{		
		if (e.getSource() instanceof JMenuItem) 
		{
			JMenuItem m = (JMenuItem)e.getSource(); //Aller chercher la source
			
			if(m.getText().equals("Afficher une vente de jardin")) //Si la source vient du JMenuItem "Afficher une vente de jardin"
			{				
				JTextField date = new JTextField(25);				
				JTextField tel = new JTextField(30);
				
				Object[] affichVente=
					{
						"Date (AA-MM-DD): ",date,
						"Telephone (AAA-BBB-CCCC): ",tel
					};
				
				int rep= JOptionPane.showConfirmDialog
				(
					this,
					affichVente,
					"Afficher une vente",
					JOptionPane.OK_CANCEL_OPTION												
				);
				
				String repDate=date.getText();
				String repTel=tel.getText();
				
				if(rep == JOptionPane.OK_OPTION)
				{
					Vente aAfficher = laBase.getVente(repTel, repDate);
					if( aAfficher != null)
						JOptionPane.showConfirmDialog
						(
							this,
							"Les infos sur "+repTel+": "+aAfficher,
							"VENTE TROUVÉE",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE
						);
					
					else
						JOptionPane.showConfirmDialog
						(
							this,
							"\""+repTel + "\" n'est pas dans la banque de données",
							"VENTE NON TROUVÉE !",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE
						);						
				}
			}
			
			if(m.getText().equals("Ayant comme produit...")) //Si la source vient du JMenuItem "Ayant comme produit..." et ainsi de suite pour les autres.
			{				
				String produit=JOptionPane.showInputDialog
				(
					this,
					"Produit (1 à la fois/Générique/aucune majuscule/singulier Ex:pomme): ",
					"Ayant comme produit...",
					JOptionPane.QUESTION_MESSAGE
				);				
				if(produit==null)
					JOptionPane.getRootFrame().dispose();	//Pour fermer la fenêtre JOptionPane				
				else
				{					
					List<Vente> lstProd = laBase.chercheProduit(produit);
					String repProduit="";
					for(Vente j : lstProd)
					{
						repProduit+=(j.toString()+"\n");
					}
					if(repProduit!="")
						JOptionPane.showConfirmDialog
						(
							this,
							"Les ventes ayant comme produit: \""+produit+"\" sont :\n"+repProduit,
							"VENTE(S) TROUVÉE(S)",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE
						);
					else
						JOptionPane.showConfirmDialog
						(
							this,
							"Le produit \""+produit + "\" n'est associé à aucune vente de jardin pour l'instant désolé",
							"VENTE(S) NON TROUVÉE(S) !",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE
						);						
				}
			}
			
			if(m.getText().equals("Correspondant au quartier..."))
			{				
				String lieu=JOptionPane.showInputDialog
				(
					this,
					"Tapez le quartier (1ere lettre majuscule.Ex:Rosemont/): ",
					"Correspondant au quartier...",
					JOptionPane.QUESTION_MESSAGE												
				);
				
				if(lieu==null)
					JOptionPane.getRootFrame().dispose();					
				else									
				{	
					String repLieu= laBase.chercheLieu(lieu);					
					if(!(repLieu.isEmpty()))
						JOptionPane.showConfirmDialog
						(
							this,
							repLieu,
							"VENTE(S) TROUVÉE(S)",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE
						);
					else
						JOptionPane.showConfirmDialog
						(
							this,
							"Le quartier \""+lieu + "\" n'est associée à aucune vente de jardin pour l'instant désolé",
							"VENTE(S) NON TROUVÉE(S) !",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE
						);
				}
			}
			
			if(m.getText().equals("Ayant comme date..."))
			{				
				String date=JOptionPane.showInputDialog
				(
					this,
					"Tapez la Date (AA-MM-DD): : ",
					"Ayant comme date...",
					JOptionPane.QUESTION_MESSAGE												
				);
				if(date==null)
					JOptionPane.getRootFrame().dispose();					
				else				
				{
					Collection<Vente> colDate = laBase.getVenteDate(date);
					if(colDate==null)
						JOptionPane.showConfirmDialog
						(
							this,
							"La date \""+date+"\" n'est associée à aucune vente de jardin pour l'instant désolé",
							"VENTE(S) NON TROUVÉE(S) !",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE
						);
					else
					{
						String repDate="";					
						for(Vente j : colDate)
							repDate+=(j.toString()+"\n");					
						JOptionPane.showConfirmDialog
						(
							this,
							"Les ventes associé à la \""+date+"\" sont :\n"+repDate,
							"VENTE(S) TROUVÉE(S)",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE
						);					
					}
				}
			}			
			
			if(m.getText().equals("Sauvegarde bdd dans fichier (saveBdd)"))
			{				
				String save=JOptionPane.showInputDialog
				(
					this,
					"Tapez le fichier a sauvegarder: ",
					"saveBdd",
					JOptionPane.QUESTION_MESSAGE												
				);
				if(save==null)
					JOptionPane.getRootFrame().dispose();
				else
					laBase.saveBdd(save);					
			}
			
			if(m.getText().equals("Charger une bdd (loadBdd)"))
			{				
				String load=JOptionPane.showInputDialog
				(
					this,
					"Tapez le fichier a charger: ",
					"loadBdd",
					JOptionPane.QUESTION_MESSAGE												
				);
				
				if(load==null)
					JOptionPane.getRootFrame().dispose();					
				else
					laBase.loadBdd(load);
			}
			
			if(m.getText().equals("Ajouter infos à la bdd (addBdd)"))
			{				
				String add=JOptionPane.showInputDialog
				(
					this,
					"Tapez fichier auquel on ajoute ses infos à la bdd existante: ",
					"addBdd",
					JOptionPane.QUESTION_MESSAGE												
				);
				if(add==null)
					JOptionPane.getRootFrame().dispose();
				else
					laBase.addBdd(add);				
			}			
			
			if(m.getText().equals("À propos de Ton-marché"))
	        {
	            JOptionPane.showMessageDialog
	            (
	        		this,
	        		"Numéro de version:1.01\nNoms des concepteurs: Gabriel Sincan et Mathieu Trudeau\nCopyright \u24B8 2020 Ton-marchéIndustries CA,Inc. All rights reserved",
	        		"À propos de Ton-marché",
	                JOptionPane.PLAIN_MESSAGE
	            );
	        }
			
			if(m.getText().equals("Quitter"))
			{
				System.exit(0);
			}
		}
 	}  //Fin du actionPerformed
	
	public static void main(String[] args) 
    {    	
    	new MenuVente(1200,300,"Ton-marché");   	
    }	
	
} //Fin classe MenuVente
