package mapping;

import Service.Library;
import Service.Rent;
import dao.RentDAO;

public class RentMapperImpl implements RentMapper {

    @Override
    public RentDAO toDAO(Rent rent) {
        if ( rent == null ) {
            return null;
        }

        RentDAO rentDAO = new RentDAO();

        return rentDAO;
    }

    @Override
    public Rent fromDAO(RentDAO rentDao) {
        if ( rentDao == null ) {
            return null;
        }

        Library library = null;

        Rent rent = new Rent( library );

        return rent;
    }

}
