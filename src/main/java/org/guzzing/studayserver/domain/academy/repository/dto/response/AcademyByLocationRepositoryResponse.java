package org.guzzing.studayserver.domain.academy.repository.dto.response;

public interface AcademyByLocationRepositoryResponse {

     long getAcademyId();
     String getAcademyName();
     String getFullAddress();
     String getPhoneNumber();
     double getLatitude();
     double getLongitude();
     String getShuttleAvailable();
     long getCategoryId();
}
