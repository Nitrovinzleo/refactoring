package fr.efrei.transfret;

import java.util.Map;

public class AssembleurCalculateurPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public AssembleurCalculateurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = tarifs;
        this.remises = remises;
    }

    public CalculPrix assembler(ContexteCalculPrix contexte) {
        TarifTransport tarifTransport = tarifs.get(contexte.mode());
        if (tarifTransport == null) {
            throw new IllegalArgumentException("Mode inconnu : " + contexte.mode());
        }

        CalculPrix chaine = new TarifBaseComponent(tarifTransport);
        chaine = new SupplementFragile(chaine);
        chaine = new SupplementDangereux(chaine);
        chaine = new SupplementRefrigere(chaine);
        chaine = new SupplementUrgence(chaine);

        PolitiqueRemise remise = remises.getOrDefault(contexte.typeClient(), new RemiseParticulier());
        chaine = new DecorateurRemise(chaine, remise);

        chaine = new DecorateurAssurance(chaine);
        return chaine;
    }
}
