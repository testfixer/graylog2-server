/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog2.indexer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graylog2.indexer.indexset.IndexSetConfig;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexMapping5Test {
    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();
    private final IndexMapping5 indexMapping = new IndexMapping5();

    @Test
    void generateDefaultTemplate() throws Exception {
        final IndexSetConfig indexSetConfig = mock(IndexSetConfig.class);
        when(indexSetConfig.indexAnalyzer()).thenReturn("standard");
        final Map<String, Object> sampleIndexTemplate = indexMapping.toTemplate(indexSetConfig, "sampleIndexTemplate");

        JSONAssert.assertEquals(json(sampleIndexTemplate), resourceFile("expected_template5.json"), true);
    }

    private String json(Object value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    private String resourceFile(String filename) {
        try {
            final URL resource = this.getClass().getResource(filename);
            if (resource == null) {
                Assert.fail("Unable to find resource file for test: " + filename);
            }
            final Path path = Paths.get(resource.toURI());
            final byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
