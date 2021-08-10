package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Root;
import org.json.JSONObject;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@BenchmarkMode(Mode.Throughput)
public class Benchmark {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JSON;

    static {
        try {
            JSON = Files.readString(Paths.get("src/main/resources/testdata/testdata.json"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public String measureOrgJson() {

        JSONObject jsonObject = new JSONObject(JSON);

        jsonObject.put("test15", UUID.randomUUID().toString());

        return jsonObject.toString();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public String measureJackson() throws JsonProcessingException {

        Root root = OBJECT_MAPPER.readValue(JSON, Root.class);

        root.setTest15(UUID.randomUUID().toString());

        return OBJECT_MAPPER.writeValueAsString(root);
    }
}
