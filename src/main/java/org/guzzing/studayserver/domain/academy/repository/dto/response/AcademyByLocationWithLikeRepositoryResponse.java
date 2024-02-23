package org.guzzing.studayserver.domain.academy.repository.dto.response;

import lombok.Getter;

import java.util.Objects;

@Getter
public class AcademyByLocationWithLikeRepositoryResponse implements AcademyByLocationRepositoryResponse{

    private final long academyId;
    private final String academyName;
    private final String fullAddress;
    private final String phoneNumber;
    private final double latitude;
    private final double longitude;
    private final String shuttleAvailable;
    private final boolean isLiked;
    private final long categoryId;

    public AcademyByLocationWithLikeRepositoryResponse(
        long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        double latitude,
        double longitude,
        String shuttleAvailable,
        boolean isLiked,
        long categoryId) {
        this.academyId = academyId;
        this.academyName = academyName;
        this.fullAddress = fullAddress;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shuttleAvailable = shuttleAvailable;
        this.isLiked = isLiked;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademyByLocationWithLikeRepositoryResponse that = (AcademyByLocationWithLikeRepositoryResponse) o;
        return academyId == that.academyId && Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && isLiked == that.isLiked && categoryId == that.categoryId && Objects.equals(academyName, that.academyName) && Objects.equals(fullAddress, that.fullAddress) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(shuttleAvailable, that.shuttleAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(academyId, academyName, fullAddress, phoneNumber, latitude, longitude, shuttleAvailable, isLiked, categoryId);
    }
}
