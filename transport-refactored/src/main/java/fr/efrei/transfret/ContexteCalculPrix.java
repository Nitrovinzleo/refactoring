package fr.efrei.transfret;

import java.util.List;

public record ContexteCalculPrix(
        List<Colis> colis,
        int distance,
        ModeTransport mode,
        boolean urgent,
        boolean assurance,
        TypeClient typeClient
) {
}
