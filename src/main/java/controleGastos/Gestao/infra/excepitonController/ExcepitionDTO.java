package controleGastos.Gestao.infra.excepitonController;

import java.time.Instant;
import java.util.List;

public record ExcepitionDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDTO> errors
) {
}
