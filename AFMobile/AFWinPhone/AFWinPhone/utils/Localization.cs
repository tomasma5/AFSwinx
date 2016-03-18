using AFWinPhone.enums;
using System;
using System.Diagnostics;
using Windows.ApplicationModel.Resources;
using Windows.Globalization;

namespace AFWinPhone.utils
{
    public class Localization
    {
        private static SupportedLanguages currentLanguage;
        private static String pathToStrings; //must be set externally

        public static String translate(String resource)
        {
            try
            {
                ResourceLoader loader = ResourceLoader.GetForCurrentView();
                String editedResource = resource.Replace('.', '/');
                if(String.IsNullOrEmpty(loader.GetString(editedResource))){
                    Debug.WriteLine("Localization text " + resource + " not found");
                    return resource;
                }
                Debug.WriteLine("Localization for is " + loader.GetString(editedResource));
                return loader.GetString(editedResource);
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.StackTrace);
                return resource;
            }
        }

        public static void changeLanguage(SupportedLanguages lang)
        {
            ApplicationLanguages.PrimaryLanguageOverride = lang.getLang();
            currentLanguage = lang;
        }

        public static SupportedLanguages getCurrentLanguage()
        {
            return currentLanguage;
        }

        public static void setPathToStrings(String path)
        {
            pathToStrings = path;
        }

    }
}
