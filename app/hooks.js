import { useEffect, useState } from "react";
import CodePush from "react-native-code-push";

const useCodePush = () => {
  const [isUpdating, setIsUpdating] = useState(true);
  const [syncProgress, setSyncProgress] = useState(null);

  useEffect(() => {
    const checkAndGetCodePush = async () => {
      try {
        const update = await CodePush.checkForUpdate();
        // 필수(mandatory) 업데이트가 존재하는 경우 업데이트 프로세스 실행
        if (update && update?.isMandatory) {
          update
            .download(progress => setSyncProgress(progress))
            .done(newPackage =>
              newPackage.install().done(() => {
                CodePush.restartApp();
              }),
            );
          return;
        }
        // 필수(mandatory) 업데이트가 존재하지 않는 경우 isUpdating 상태 false로 변경
        setIsUpdating(false);
        return;
      } catch (err) {
        setIsUpdating(false);
      }
    };

    checkAndGetCodePush();
  }, []);

  return [isUpdating, syncProgress];
};

export default useCodePush;
