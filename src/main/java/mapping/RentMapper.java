package mapping;

import Service.Rent;
import dao.RentDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RentMapper {

    RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

    RentDAO toDAO(Rent rent);
    Rent fromDAO(RentDAO rentDao);

}
