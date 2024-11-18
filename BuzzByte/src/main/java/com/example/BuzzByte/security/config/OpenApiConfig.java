//package com.example.BuzzByte.security.config;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.annotations.servers.Server;
//
//@OpenAPIDefinition(
//        info = @Info(
//                contact = @Contact(name = "WorksOnMyMachine"),
//                description = "OpenApi documentation for login/register",
//                title = "Login Feature ABC 2024",
//                version = "1.0",
//                summary ="This endpoint are used to manage users in the application."
//        ),
//        servers = {
//                @Server(
//                        description = "abc2024",
//                        url = "/"
//                )
//        }
//
//
//)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT authentication",
//        scheme = "Bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
//public class OpenApiConfig {
//}