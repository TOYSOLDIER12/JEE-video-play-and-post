package ma.xproce.video.service.mapper;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {
    ModelMapper creatorMapper = new ModelMapper();
    public CreatorDTO CreatorToCreatorDTO(Creator creator){
        return creatorMapper.map(creator, CreatorDTO.class);
    }
    public Creator CreatorDTOToCreator(CreatorDTO creatorDTO){
        return creatorMapper.map(creatorDTO, Creator.class);
    }
    public Creator CreatorDTOADDToCreator(CreatorDTOADD creatorDTOADD){
        return creatorMapper.map(creatorDTOADD, Creator.class);
    }
    public CreatorDTOADD CreatorToCreatorDTOADD(Creator creator){
        return creatorMapper.map(creator, CreatorDTOADD.class);
    }

}
