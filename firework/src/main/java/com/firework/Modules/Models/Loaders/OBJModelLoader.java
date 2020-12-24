package com.firework.Modules.Models.Loaders;

/**
 * Created by Slava on 18.02.2017.
 */

public class OBJModelLoader {

//    public static Model loadOBJModel(String modelName){
//
//        Model model = new Model();
//
//        ArrayList<Vector3> vertex = new ArrayList<>();
//        ArrayList<Vector3> normals = new ArrayList<>();
//        ArrayList<Vector3> textureCords = new ArrayList<>();
//        ArrayList<Vector3> vertexIndices = new ArrayList<>();
//        ArrayList<Vector3> normalIndices = new ArrayList<>();
//        ArrayList<Vector3> textureIndices = new ArrayList<>();
//
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(Loader.getFileIO().loadAsset(modelName))
//            );
//        } catch (IOException e) {
//            Log.e("Loader", e.getMessage());
//        }
//
//        String line;
//
//        try {
//            while ((line = reader.readLine()) != null) {
//                if (line.startsWith("v ")) {
//                    float x = Float.valueOf(line.split(" ")[1]);
//                    float y = Float.valueOf(line.split(" ")[2]);
//                    float z = Float.valueOf(line.split(" ")[3]);
//                    vertex.add(new Vector3(x, y, z));
//
//                } else if (line.startsWith("vn ")) {
//                    float x = Float.valueOf(line.split(" ")[1]);
//                    float y = Float.valueOf(line.split(" ")[2]);
//                    float z = Float.valueOf(line.split(" ")[3]);
//                    normals.add(new Vector3(x, y, z));
//
//                }
//                else if (line.startsWith("vt ")) {
//                    float x = Float.valueOf(line.split(" ")[1]);
//                    float y = Float.valueOf(line.split(" ")[2]);
//                    float z = Float.valueOf(line.split(" ")[3]);
//                    textureCords.add(new Vector3(x, y, z));
//                }
//                else if (line.startsWith("f ")) {
//                    // Face
//                    Vector3 vertexIndices = new Vector3(
//                            Float.valueOf(line.split(" ")[1].split("/")[0]),
//                            Float.valueOf(line.split(" ")[2].split("/")[0]),
//                            Float.valueOf(line.split(" ")[3].split("/")[0])
//                    );
//
//                    Vector3 textureIndices = new Vector3(
//                            Float.valueOf(line.split(" ")[1].split("/")[1]),
//                            Float.valueOf(line.split(" ")[2].split("/")[1]),
//                            Float.valueOf(line.split(" ")[3].split("/")[1])
//                    );
//
//                    Vector3 normalIndices = new Vector3(
//                            Float.valueOf(line.split(" ")[1].split("/")[2]),
//                            Float.valueOf(line.split(" ")[2].split("/")[2]),
//                            Float.valueOf(line.split(" ")[3].split("/")[2])
//                    );
//
//                    indices.add(vertexIndices, textureIndices, normalIndices);
//                }
//            }
//        } catch (IOException e) {
//            Log.e("Loader", e.getMessage());
//        }
//
//        try {
//            reader.close();
//        } catch (IOException e) {
//            Log.e("Loader", e.getMessage());
//        }
//
//        return model;
//    }
}