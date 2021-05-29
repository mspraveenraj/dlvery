package com.project.dlvery.security.oauth2;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.dlvery.entity.AuthProvider;
import com.project.dlvery.entity.Team;
import com.project.dlvery.entity.User;
import com.project.dlvery.exception.OAuth2AuthenticationProcessingException;
import com.project.dlvery.repository.TeamRepository;
import com.project.dlvery.repository.UserRepository;
import com.project.dlvery.security.UserPrincipal;
import com.project.dlvery.security.oauth2.user.OAuth2UserInfo;
import com.project.dlvery.security.oauth2.user.OAuth2UserInfoFactory;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
        	
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
            	System.out.println("provider error");
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
            
        } else {
        	throw new OAuth2AuthenticationProcessingException("You are not existing user. Please contact your Administrator");
            //user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
            //System.out.println(user.getEmail()+ "register user error");
        }
       
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

//    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
//        User user = new User();
//
//        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
//        user.setProviderId(oAuth2UserInfo.getId());
//        String nameSplit[] = oAuth2UserInfo.getName().split(" ");
//        user.setFirstName(nameSplit[0]);
//        if(nameSplit[1] != "")
//        	user.setLastName(nameSplit[1]);
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setUsername(oAuth2UserInfo.getEmail());
//        
//        Optional<Team> teamOptional = teamRepository.findByTeamName("DLTeam");
//       if(teamOptional.isPresent())
//    	   user.setTeam(teamOptional.get());
//        //user.setImageUrl(oAuth2UserInfo.getImageUrl());
//        System.out.println(user.getEmail()+" at register new user");
//        return userRepository.save(user);
//    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    	String name[] = oAuth2UserInfo.getName().split(" ");
        existingUser.setFirstName(name[0]);
        if(name[1].length()>0) {
        	existingUser.setLastName(name[1]);
        }
        //existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}