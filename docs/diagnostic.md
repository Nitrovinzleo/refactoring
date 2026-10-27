# Diagnostic de l'architecture historique (Étape 1)

## 1. Tableau des violations SOLID

| Défaut | Emplacement | Principe violé | Description |
|---|---|---|---|
| Responsabilités multiples dans un service | `ServiceExpedition` | **SRP** (Single Responsibility) | Gère la validation des demandes, l'estimation des délais, le changement d'état, l'envoi des notifications (email/sms) et les requêtes DB. |
| Génération de facture dans l'entité | `Expedition.genererFacture()` | **SRP** (Single Responsibility) | L'entité métier porte la responsabilité de la mise en page et de l'affichage textuel de la facture. |
| Cascades de `if/else` sur les modes et clients | `CalculateurPrix.calculerPrixHT()` | **OCP** (Open/Closed) | Chaque ajout de mode de transport ou de type de remise nécessite de modifier directement la méthode. |
| Dispatch du type de véhicule selon le mode | `AffectationVehicule.trouverVehicule()` | **OCP** (Open/Closed) | Enchaînement de conditions et de vérifications `instanceof` fermées à l'extension. |
| Refus de colis dans une sous-classe | `CamionFrigorifique.charger()` | **LSP** (Liskov Substitution) | Ne peut pas se substituer à un `Camion` standard car il rejette les colis non réfrigérés avec une exception. |
| Interface véhicule trop large avec méthodes rejetées | `IVehicule` (`Camion`, `Bateau`, `Avion`, `Train`) | **ISP** (Interface Segregation) | Impose `rouler`, `naviguer`, `voler`, `activerRefrigeration`. Les implémentations lèvent `UnsupportedOperationException`. |
| Couplage fort aux implémentations concrètes et statiques | `ServiceExpedition`, `ServiceClient`, `LegacyAdapter` | **DIP** (Dependency Inversion) | Dépendance directe aux singletons (`Database.getInstance()`), aux classes concrètes (`new CalculateurPrix()`, `new SmsService()`) et aux méthodes statiques (`EmailService.envoyer()`). |

---

## 2. Impact des évolutions cibles sur le code historique

### Évolution A : Ajout du mode `FLUVIAL`
Pour ajouter le mode `FLUVIAL` dans le code existant, il faut modifier 6 fichiers distincts :
1. `CalculateurPrix.java` : ajouter le calcul du tarif (0,40 €/km + 0,02 €/kg, minimum 300 €).
2. `ServiceExpedition.java` :
   - autoriser `FLUVIAL` dans la validation ;
   - interdire le réfrigéré et l'urgence pour ce mode ;
   - calculer le délai de livraison (`distance / 360 + 1`) ;
   - adapter l'action au démarrage (`demarrerExpedition`).
3. `AffectationVehicule.java` : ajouter le cas `mode.equals("FLUVIAL")` et la sélection de la péniche.
4. `Flotte.java` : ajouter la péniche `PEN-0001` (capacité 2 500 t).
5. `IVehicule.java` / `Bateau.java` (ou nouveau `Peniche.java`) : adapter les méthodes de déplacement.
6. `Utils.java` : ajouter la distance pour le trajet fluvial (ex. Paris → Le Havre : 365 km).

### Évolution B : Ajout du supplément « hors gabarit » (+10 %)
1. `CalculateurPrix.java` :
   - inspecter chaque colis pour vérifier si une dimension dépasse 200 cm ;
   - appliquer la multiplication par 1,10 au bon rang dans la séquence des suppléments.
