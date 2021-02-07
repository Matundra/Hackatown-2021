/*Fichier TestInterface.java

   auteur : Mathieu Trudeau et Gabriel Sincan

   Ce programme permet de créer une interface "TestInterface" pour garantir une syntaxe de programmation commune.

   Derniere mise a jour : 7 février 2021
*/
import java.util.*;

public interface TestInterface {

    public void addVente(Vente uneVente);

	public Vente getVente(String tel, String date);

	public void addBdd(String nomFile);

	public void loadBdd(String nomFile);

	public ArrayList<Vente> chercheProduit(String produit);

	public Collection<Vente> getVenteDate(String date);

	public void saveBdd(String nomFichier);

	public String chercheLieu(String lieu);
}