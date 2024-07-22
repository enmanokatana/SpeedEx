package org.example.server.Documentation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.server.mappers.DocumentDtoMapper;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final WorkspaceRepository workspaceRepository;
    private final DocumentDtoMapper mapper = new DocumentDtoMapper();
    private final ResponseDto responseDto = new ResponseDto();

    @Transactional
    public ResponseDto addDocumentToWorkspace(Long documentId, Long workspaceId){
        var workspace = workspaceRepository.findById(workspaceId);
        if (workspace.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("workspace doesn't Exist");
            return responseDto;        }
        var document = documentRepository.findById(documentId);
        if (document.isEmpty()){
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("document doesn't Exist");
            return responseDto;        }
        document.get().getWorkspaces().add(workspace.get());
        workspace.get().getDocuments().add(document.get());



        ///

        documentRepository.save(document.get());
        workspaceRepository.save(workspace.get());


        responseDto.setWorked(true);
        responseDto.setResult(mapper.apply(document.get()));
        responseDto.setMessage("Saved document with  Success");
        return responseDto;
    }


    public ResponseDto uploadDocument(MultipartFile file ,
                                      String name ,
                                      String description,
                                      Category category){

        //todo decide where and how to store
        return responseDto;
    }
    

}

