package service;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractService {
    protected static ObjectMapper mapper = new ObjectMapper();
}
