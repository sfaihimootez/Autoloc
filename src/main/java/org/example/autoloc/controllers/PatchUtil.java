package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

final class PatchUtil {
    private PatchUtil() {}

    static <T> T apply(T target, JsonNode patch, ObjectMapper mapper) {
        if (patch == null || !patch.isObject()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le corps PATCH doit être un objet JSON");
        }
        try {
            mapper.readerForUpdating(target).readValue(mapper.treeAsTokens(patch));
            return target;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corps PATCH invalide", e);
        }
    }
}
