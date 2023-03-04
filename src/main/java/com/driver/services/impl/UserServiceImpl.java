package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
    // create a user of given country. The originalIp of the user should be "countryCode.userId" and
    // return the user. Note that right now user is not connected and thus connected would be false and
    // maskedIp would be null
    // Note that the userId is created automatically by the repository layer

        User user = new User();
       user.setUsername(username);
       user.setPassword(password);

        Country country = new Country();
        switch (countryName.toLowerCase()){
            case "ind":
                country.setCountryName(CountryName.IND);
                country.setCode(CountryName.IND.toCode());
                break;
            case "usa":
                country.setCountryName(CountryName.USA);
                country.setCode(CountryName.USA.toCode());
                break;
            case "aus":
                country.setCountryName(CountryName.AUS);
                country.setCode(CountryName.AUS.toCode());
                break;
            case "chi":
                country.setCountryName(CountryName.CHI);
                country.setCode(CountryName.CHI.toCode());
                break;
            case "jpn":
                country.setCountryName(CountryName.JPN);
                country.setCode(CountryName.JPN.toCode());
                break;
        }
        country.setUser(user);
        user.setOriginalCountry(country);
        user.setConnected(false);
        user.setMaskedIp(null);

        //Generating original IP
        String code = country.getCode()+"."+userRepository3.save(user).getId();
        user.setOriginalIp(code);

        userRepository3.save(user);
        return user;

    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        user.getServiceProviderList().add(serviceProvider);
       serviceProvider.getUsers().add(user);

        serviceProviderRepository3.save(serviceProvider);
        return user;
    }
}
/*

Users: Register themselves using the registerUser endpoint. Each time you register a user,
 you should create a new Country object based on the given country name and assign it as the original
 country of the user. Note that the service provider attribute of the country in this case would be null.
 Subscribe to a service provider using to subscribe endpoint

 */