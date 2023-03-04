package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
       Admin admin = new Admin();
       admin.setUsername(username);
       admin.setUsername(password);

       adminRepository1.save(admin);
       return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {

        Admin admin = adminRepository1.findById(adminId).get();

        //setting the attributes
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setAdmin(admin);
        serviceProvider.setName(providerName);

        admin.getServiceProviders().add(serviceProvider);
        adminRepository1.save(admin);

        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        /*add a country under the serviceProvider and return respective service provider
        country name would be a 3-character string out of ind, aus, usa, chi, jpn. Each character can be in uppercase or lowercase. You should create a new Country object based on the given country name and add it to the country list of the service provider. Note that the user attribute of the country in this case would be null.
        In case country name is not amongst the above mentioned strings, throw "Country not found" exception
        */

       ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

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
            default:
                throw new Exception("Country not found");
        }
        country.setUser(null);
        country.setServiceProvider(serviceProvider);
        serviceProvider.getCountryList().add(country);
        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;



    }
}
