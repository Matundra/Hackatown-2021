/*Fichier Vente.java

   auteur : Mathieu Trudeau et Gabriel Sincan

   Ce programme permet de cr�er la classe "Vente" (impl�mentant Comparable) en cr�ant ses constructeurs et ses m�thodes qui seront utilis�es pour interagir avec des collections de ventes dans le fichier Bdd.java

   Derniere mise a jour : 7 f�vrier 2021
*/
import java.util.*;

public class Vente implements Comparable<Vente>
{	
	private String date;
	private String tel;
	private String lieu;
	private Set<String>produit;	
	
	public Vente(String date,String tel,String lieu)
	{
		this.date=date;
		this.tel=tel;
		this.lieu=lieu;
		produit=new LinkedHashSet<String>();
	}

	public Vente(String date,String tel,String lieu,Set <String>produit)
	{
		this.date=date;
		this.tel=tel;
		this.lieu=lieu;
		this.produit=produit;
	}
	
	public String toString()	//Pour afficher l'objet de la classe Vente de fa�on conforme � l'affichage de celle de la base de donn�e (fichier.txt)
	{
		String res=date+";"+tel+";"+lieu+";";		
		String resProduit="";	
		
		if(!(produit.isEmpty()))
		{
			Iterator <String>k = produit.iterator();			
			while(k.hasNext())
			{				
				resProduit+=(k.next()+",");								
			}
			res+=resProduit.substring(0,resProduit.length()-1);	//Enlever "," a la fin
		}		
		return res;		
	}

	public void addProduit(Set<String>produit)	//M�thode pour ajouter une collection (Set) dans la collection (Set) "produit"
	{
		produit.addAll(produit);
	}

	public void addProduit(String c)  //M�thode pour ajouter un produit (String) dans la collection (Set) "produit"
	{		
		produit.add(c);
	}

	public boolean chercherProduit(String c)	
	{		
		if(produit.contains(c))  //Retourne vrai si la collection (Set) contient le produit cherch� en param�tre
			return true;
		else
			return false;
	}

	public boolean equals (Object o)
	{
		Vente autre;

		if(o instanceof Vente)
		{
			autre=(Vente)o;
			return tel.equals(autre.tel) && date.equals(autre.date);   
		}
		return false;
	}

	public int compareTo(Vente autre)
	{
		int res = tel.compareTo(autre.tel);

		if(res==0)  
			res = date.compareTo(autre.date);
		return res;
	}

	public int hashCode()
	{
		return date.hashCode() + tel.hashCode();
	}

	public String getDate()   //M�thode d'acc�s de la date de la vente dans les classes ext�rieurs (Ex: Bdd.java)
	{
		return date;
	}

	public String getTel()	//M�thode d'acc�s du telephone de la vente dans les classes ext�rieurs (Ex: Bdd.java)
	{
		return tel;
	}

	public String getLieu()		//M�thode d'acc�s du lieu de la vente dans les classes ext�rieurs (Ex: Bdd.java)
	{
		return lieu;
	}

}