# 명지대 셔틀버스 App 
blog.mju-bus.com 에서 더욱 자세한 내용 확인할 수 있습니다! 

## Small Commit 
한번에 너무 많은 코드를 커밋하기 보다는, 조금씩 여러번 커밋하는 것을 **추천**합니다.  
 - 커밋 메시지가 주석과 문서를 대체할 수 있습니다.
 - 코드 리뷰시 더욱 원활하게 진행이 가능합니다. 

## Commit Message Convention 
`{Type}({Problem}) : {Description}`  

| Type     | Description    |
| --------|---------|
| feat  | 새로운 기능 추가   |
| docs | 문서 수정 |
| test  | 테스트 코드 작성/수정   |
| refactor  | 코드 리팩토링   |
| style  | 코드 의미에 영향을 주지 않는 변경사항   |
| chore  | 빌드 부분 혹은 패키지 매니저 수정사항   |

`git commit -m {}` 으로 작성하셔도 좋지만, `git commit` 후 세부 사항까지 적어주신다면 `Code Review` 에 큰 도움이 됩니다. 


## Merge 전략 (Rebase and Merge)
저희는 [Git-Flow](https://blog.mju-bus.com/Git-101-def7a751f2ef43aca602e496b0d33de9) 를 이용하여 개발 중에 있습니다.  
해당 `Flow` 에서는 `feature` 브랜치에서 `Staging` 브랜치로 `Merge` 하는 경우가 발생하게 됩니다.  
이 때, `git rebase staging` 을 통하여 로컬에서 충돌을 해결 후 `Merge` 하도록 합니다.  
  
## Code Review 
미약하지만 점진적으로 `코드 리뷰`를 도입해보려고 합니다.  
`feature` 브랜치에서 `Staging` 브랜치에 `Pull Request` 시에 `코드 리뷰`를 진행합니다.  