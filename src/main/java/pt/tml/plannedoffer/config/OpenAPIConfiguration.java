package pt.tml.plannedoffer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Planned Offer API",
                version = "V0.0.1",
                description = "REST API do micro serviço -Gestor de Oferta Planeada-",
                contact = @Contact(
                        name = "Luis Antunes",
                        email = "info@powerqubit.com",
                        url = ""
                )
        ),
        servers = {
            @Server(
                url="localhost:8080/planned-offer",
                description="DEV Server"
                )
        }
        )

class OpenAPIConfiguration
{
}