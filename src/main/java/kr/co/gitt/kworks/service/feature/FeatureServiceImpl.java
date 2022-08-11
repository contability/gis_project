package kr.co.gitt.kworks.service.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.gitt.kworks.dto.feature.FeatureKmlResultDTO;
import kr.co.gitt.kworks.dto.feature.FeatureResultDTO;
import kr.co.gitt.kworks.dto.feature.KmlPlacemarkDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.spatialdata.geometry.GeomFactory;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

@Service("featureService")
public class FeatureServiceImpl implements FeatureService {

	@Override
	public FeatureResultDTO getFeaturesFromExcelFile(MultipartFile multipartFile, Integer startLine, Integer xIndex, Integer yIndex) throws Exception {
		FeatureResultDTO result = new FeatureResultDTO();
		
		int count = 0;
		int successCount = 0;
		int failCount = 0;
		
		int xCellIndex = xIndex-1;
		int yCellIndex = yIndex-1;
		// 이승재, 2020.07.06
		// 엑셀 가져오기 시 시작 라인 인덱스를 입력 시에는 1부터
		// 처리 시에는 0부터 계산됨을 반영
		int startLineIndex = startLine - 1;
		
		List<String> features = new ArrayList<String>();
		
		Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		
		// 이승재, 2020.07.06
		// 엑셀 가져오기 시 시작라인수 계산 오류 처리
		//for(int i=0, len=startLine; i <= len; i++) {
		for(int i=0, len=startLineIndex; i < len; i++) {
			if(rows.hasNext()) {
				rows.next();
			}
		}

		while(rows.hasNext()) {
			count++;
			try {
				Row row = rows.next();
				double x = getCellValue(row.getCell(xCellIndex));
				double y = getCellValue(row.getCell(yCellIndex));
				
				IGeometry point = GeomFactory.createPoint(new Coordinate(x, y));
				features.add(point.toWKT());
				successCount++;
			}
			catch (NumberFormatException nfe) {
				failCount++;
			}
		}
		workbook.close();
		
		result.setCount(count);
		result.setSuccessCount(successCount);
		result.setFailCount(failCount);
		result.setFeatures(features);
		
		return result;
	}
	
	protected double getCellValue(Cell cell) {
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Double.parseDouble(cell.getStringCellValue());
		}
		else {
			return 0;
		}
	}
	
	@Override
	public FeatureKmlResultDTO getFeaturesFromKmlFile(File file) throws Exception {
		Kml kml = Kml.unmarshal(file);
        Feature feature = kml.getFeature();
        return parseFeature(feature);
	}
	
	private FeatureKmlResultDTO parseFeature(Feature feature) {
		FeatureKmlResultDTO featureKmlResultDTO = new FeatureKmlResultDTO();
		
        if(feature != null) {
            if(feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();
                for(Feature documentFeature : featureList) {
                	if(documentFeature instanceof Folder) {
                		Folder folder = (Folder) documentFeature;
                		
                		List<KmlPlacemarkDTO> placemarks = new ArrayList<KmlPlacemarkDTO>();
                		List<Feature> folderFeatureList = folder.getFeature();
                		for(Feature folderFeature : folderFeatureList) {
                			if(folderFeature instanceof Placemark) {
                				Placemark placemark = (Placemark) folderFeature;
                				Geometry geometry = placemark.getGeometry();
                				
                				KmlPlacemarkDTO kwsPlacemark = new KmlPlacemarkDTO();
                				kwsPlacemark.setName(placemark.getName());
                				kwsPlacemark.setDescription(placemark.getDescription());   				
                				kwsPlacemark.setGeometry(parseGeometry(geometry));
                				placemarks.add(kwsPlacemark);
                			}
                		}
                		
                		featureKmlResultDTO.setName(folder.getName());
                		featureKmlResultDTO.setPlacemarks(placemarks);
                	}
                }
            }
        }
        return featureKmlResultDTO;
    }
    
    private IGeometry parseGeometry(Geometry geometry) {
    	IGeometry geom = null;
    	
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if(outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if(linearRing != null) {
                        List<de.micromata.opengis.kml.v_2_2_0.Coordinate> coordinates = linearRing.getCoordinates();
                        if(coordinates != null) {
                            for(de.micromata.opengis.kml.v_2_2_0.Coordinate coordinate : coordinates) {
//                            	kmasCoordinate = parseCoordinate(coordinate);
                            }
                        }
                    }
                }
            }
            else if (geometry instanceof Point) {
            	Point point = (Point)geometry;
            	List<de.micromata.opengis.kml.v_2_2_0.Coordinate> coordinates = point.getCoordinates();
            	if(coordinates != null) {
                    for(de.micromata.opengis.kml.v_2_2_0.Coordinate coordinate : coordinates) {
                    	geom = getPointGeom(coordinate);
                    }
                }
            }
        }
        return geom;
    }
    
    private IGeometry getPointGeom(de.micromata.opengis.kml.v_2_2_0.Coordinate coordinate) {
    	 double x = 0;
    	 double y = 0;
    	 
        if(coordinate != null) {
            x = coordinate.getLongitude();
            y = coordinate.getLatitude();
        }
		return GeomFactory.createPoint(new Coordinate(x, y));
    }

}
