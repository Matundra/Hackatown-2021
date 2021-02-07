/*Fichier Bdd.java

   auteur : Mathieu Trudeau et Gabriel Sincan

   Ce programme permet de créer une classe (implémentant une interface) ayant des méthodes permettant d'interagir avec des bases de données (fichiers .txt ou saisies de clavier) en utilisant des collections: addition,chargement,sauvegarde de bdd;recherche et addition d'éléments dans les collections

   Derniere mise a jour : 7 février 2021
*/
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Bdd implements TestInterface  //Déclaré public car porte le même nom que le fichier.java
{
	private LinkedHashMap<String,Set<Vente>>bibliotheque= new LinkedHashMap<String,Set<Vente>>();  
	private Set<Vente> venteDate; 

    public Bdd()	//Constructeur
    {    	
    	venteDate = new TreeSet<Vente>();
    }

    public void addVente(Vente uneVente)
    {    	
    	venteDate=bibliotheque.get(uneVente.getDate());   //venteDate sera la valeur retournée par le get de la map "bibliotheque"    	
		if(venteDate !=null)    //On a déjà un/des Vente(s) associée(s) a la date
		{			
			if(venteDate.contains(uneVente))
			{	
				uneVente.addProduit("");				
			}
			venteDate.add(uneVente);			
		}
		else
		{
			venteDate= new TreeSet<Vente>();
			venteDate.add(uneVente);
			bibliotheque.put(uneVente.getDate(),venteDate);			
		}		
    }

	public Vente getVente(String tel, String date)
	{		
		Vente res= null;
		Vente aTrouver = new Vente(date,tel,"");		
		Iterator <Map.Entry<String,Set<Vente>>>i=bibliotheque.entrySet().iterator();
		Map.Entry<String,Set<Vente>> dateCourante;
		while(i.hasNext())
		{
			dateCourante = i.next();			
			if(date.equals(dateCourante.getKey()))	//Si la Map.Entry a déjà une clé égale a la date de la vente cherchée
			{				
				Set<Vente> dateCherchee =dateCourante.getValue();
				Iterator <Vente>k = dateCherchee.iterator();
				while(k.hasNext())
				{
					res=k.next();
					if(aTrouver.equals(res))
						break;	//Sort de la boucle quand la vente a trouver est "equals" a la vente dans le Set.					
					else res=null;
				}
			}
		}
		return res;
	}

	public void addBdd(String nomFile)
	{
		FileReader fr = null;
		boolean existeFile = true;
		boolean finFichier = false;

		try
		{
		   fr = new FileReader(nomFile);

			if (existeFile)
			{
			  BufferedReader entree = new BufferedReader (fr);
			  while (!finFichier)
			  {
				 String ligne = entree.readLine();
				 if (ligne != null)  //Si ligne non nulle créer uneVente
				 {
				   	String[] champs=ligne.split(";",4);
				    String date = champs[0];
				    String tel = champs[1];
				    String lieu = champs[2];
					String[] tabProduit = champs[3].split(",");					
					Vente AutreVente=new Vente(date,tel,lieu);	
					for(String produit:tabProduit)   //Ajout de chaque produit pour une vente
					{						
						AutreVente.addProduit(produit);
					}
					
					venteDate=bibliotheque.get(AutreVente.getDate());   //venteDate sera la valeur retourné par le get de la map "bibliotheque"					
							if(venteDate !=null)    //On a déjà une valeur(s) associée(s) a la date
							{								
								for (Iterator<Vente> iterator = venteDate.iterator(); iterator.hasNext();)
								{									
									Vente res = iterator.next();
									if(AutreVente.getTel().equals(res.getTel())) //Si la vente existe déjà
										iterator.remove();	//Supprime l'ancien																	
								}
								venteDate.add(AutreVente); 
							}
							else
							{
								venteDate= new TreeSet<Vente>();
								venteDate.add(AutreVente);
								bibliotheque.put(AutreVente.getDate(),venteDate);
							}
				  }
				  else finFichier = true;				 
			  	}
			  entree.close();
			  JOptionPane.showConfirmDialog
			  (
				  null,
				  "Le fichier \""+nomFile+"\" a été chargé ou ajouté",
				  "Fichier chargé/ajouté",
				  JOptionPane.DEFAULT_OPTION,
				  JOptionPane.INFORMATION_MESSAGE
			  );			  
			}
		}
		catch (java.io.FileNotFoundException e)
		{
			JOptionPane.showConfirmDialog
			(
				null,
				"Probleme d'ouvrir le fichier " + nomFile,
				"java.io.FileNotFoundException e",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE
			);
			existeFile = false;
		}
		catch (IOException e)
		{
			JOptionPane.showConfirmDialog
			(
				null,
				"Erreur lors de la lecture du fichier",
				"IOException e",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE
			);			
		}		
	}

	public void loadBdd(String nomFile)
	{
		bibliotheque = new LinkedHashMap<String, Set<Vente>>();		//Création d'une nouvelle LinkedHashMap vide (pour effacer les anciennes données)
		addBdd(nomFile);		
	}

	public ArrayList<Vente> chercheProduit(String produit)
	{
		ArrayList<Vente> venteAyantProduit = new ArrayList<Vente>();
		Vente res= new Vente("","","");
		Iterator <Map.Entry<String,Set<Vente>>>i=bibliotheque.entrySet().iterator();
		Map.Entry<String,Set<Vente>> dateCourante;
		while(i.hasNext())
		{
			dateCourante = i.next();
			Set<Vente> dateCherchee =dateCourante.getValue();
			Iterator <Vente>k = dateCherchee.iterator();
			while(k.hasNext())
			{
				res=k.next();
				if(res.chercherProduit(produit))  //Appelle de la méthode booléenne chercherProduit(String c) dans Vente.java
					venteAyantProduit.add(res);		//Si la collection (Set) de produit dans la Vente "res" contient le produit cherché en paramètre alors met cette vente dans le ArrayList
			}
		}
		return venteAyantProduit;
	}

	public Collection<Vente> getVenteDate(String date)
	{
		Collection<Vente> venteAyantDate =  new ArrayList<Vente>();
		Vente res= new Vente("","","");
		Iterator <Map.Entry<String,Set<Vente>>>i=bibliotheque.entrySet().iterator();
		Map.Entry<String,Set<Vente>> dateCourante;
		while(i.hasNext())
		{
			dateCourante = i.next();
			if(date.equals(dateCourante.getKey()))
			{
				Set<Vente> dateCherchee =dateCourante.getValue();
				Iterator <Vente>k = dateCherchee.iterator();
				while(k.hasNext())
				{
					res=k.next();
					venteAyantDate.add(res);   //Add la vente "res" dans la collection si sa date (passée en paramètre) est "equals" à la clé de la Map.Entry
				}
			}
		}
		if (venteAyantDate.isEmpty())
			venteAyantDate=null;
		return venteAyantDate;
	}

	public void saveBdd(String nomFichier)
	{
		boolean probleme = false;
		FileWriter fw = null;
		try
		{
			fw = new FileWriter (nomFichier);
			if (!probleme)
			{
				//System.out.println("\nDébut de la création du fichier\n");
				PrintWriter aCreer = new PrintWriter (fw);

				Vente res= new Vente("","","");
				Iterator <Map.Entry<String,Set<Vente>>>i=bibliotheque.entrySet().iterator();
				Map.Entry<String,Set<Vente>> dateCourante;
				while(i.hasNext())
				{
					dateCourante = i.next();
					Set<Vente> dateCherchee =dateCourante.getValue();
					Iterator <Vente>k = dateCherchee.iterator();
					while(k.hasNext())
					{
						res=k.next();
						String texte=res.toString();   //Transforme l'objet "res" de type Vente en String (en utilisant la méthode toString() dans Bdd.java) pour que son format d'affichage soit le même que pour la lecture du fichier .txt (présence de ";" et de ",")
						aCreer.printf("%s\n",texte);
					}
				}
				aCreer.close();
				//System.out.println("Fin de la création du fichier "+nomFichier+"\n\n");
				JOptionPane.showConfirmDialog
				(
					null,
					"Le fichier \""+nomFichier+"\" a été créé",
					"Fichié sauvegardé",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE
				);
			}
		}
		catch (java.io.FileNotFoundException erreur)
		{
			JOptionPane.showConfirmDialog
			(
				null,
				"Problème de préparer l'écriture",
				"java.io.FileNotFoundException erreur",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE
			);			
			probleme = true;
		}
		catch (IOException e)
		{
			JOptionPane.showConfirmDialog
			(
				null,
				"Erreur lors de l'écriture du fichier",
				"IOException e",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE
			);			
		}
	}

	public String chercheLieu(String lieu)
	{				
		String texte="";
		Vente aTrouver = new Vente("","",lieu);
		Iterator <Map.Entry<String,Set<Vente>>>i=bibliotheque.entrySet().iterator();
		Map.Entry<String,Set<Vente>> dateCourante;		
		while(i.hasNext())
		{
			dateCourante = i.next();
			Set<Vente> dateCherchee =dateCourante.getValue();
			Iterator <Vente>k = dateCherchee.iterator();
			while(k.hasNext())
			{
				Vente res=k.next();
				if(res.getLieu().equals(aTrouver.getLieu())) //Si le lieu de la vente a trouver (passée en paramètre) est "equals" au lieu d'une vente dans le Set.
				{					
					texte+=res.toString()+"\n";//Transforme l'objet "res" de type Vente en String pour que son format d'affichage soit dictée par la méthode toString dans Bdd.java					
					//System.out.printf("%s\n",texte);					
				}
			}
		}
		return texte;
	}
	
}