package com.testsample;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {

            List<Data> data = Arrays.asList(mapper.readValue(Paths.get("C:\\dataClients.json").toFile(), Data[].class));

            List<Data> age20to30 = data
                    .stream()
                    .filter(p -> p.getAge() >= 20 && p.getAge() < 30)
                    .sorted(Comparator.comparing(p -> p.getLastName() + " " + p.getFirstName()))
                    .collect(Collectors.toList());
            age20to30.forEach(System.out::println);

            Set<String> cities = data
                    .stream()
                    .map(Data::getCity)
                    .collect(Collectors.toCollection(TreeSet::new));
            cities.forEach(System.out::println);

            Map<String, Long> byAges = data
                    .stream()
                    .collect(Collectors.groupingBy(Data::getAgeGroup, TreeMap::new, Collectors.counting()));
            System.out.println(byAges);


            Map<String, Object> map = new HashMap<>();
            map.put("age20To30", age20to30);
            map.put("cities", cities);
            map.put("byAges", byAges);

            // convert map to JSON file
            mapper.writeValue(Paths.get("D:\\dataOutput.json").toFile(), map);


        } catch (JsonParseException jsonParseException) {
            jsonParseException.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
